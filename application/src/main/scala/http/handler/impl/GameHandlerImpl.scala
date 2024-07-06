package http.handler.impl

import flow.*
import flow.impl.GetGameDetailsResult
import http.handler.GameHandler
import kafka.producer.EventsProducer
import model.{PieceType, Position}
import zio.{Task, ZLayer}

case class GameHandlerImpl(
  addPieceFlow: AddPieceFlow,
  createNewGameFlow: CreateNewGameFlow,
  getGameDetailsFlow: GetGameDetailsFlow,
  deletePieceFlow: DeletePieceFlow,
  movePieceFlow: MovePieceFlow,
  eventsProducer: EventsProducer)
    extends GameHandler {

  override def createNewGame: Task[String] =
    createNewGameFlow
      .run()
      .tap(newGameId => eventsProducer.produce(s"new game with ID [$newGameId] created"))

  override def getGameDetails(gameId: String): Task[GetGameDetailsResult] =
    getGameDetailsFlow
      .run(gameId = gameId)
      .tap(result => eventsProducer.produce(s"game details for game with ID [${result.gameId}] retrieved"))

  override def addPiece(gameId: String, pieceType: PieceType, targetPosition: Position): Task[Int] =
    addPieceFlow
      .run(gameId = gameId, pieceType = pieceType, targetPosition = targetPosition)
      .tap(result => eventsProducer.produce(s"new piece of type [${pieceType.code}] added to game with ID [$gameId]. New piece's ID is $result"))

  override def deletePiece(gameId: String, pieceId: Int): Task[Unit] =
    deletePieceFlow
      .run(gameId = gameId, pieceId = pieceId)
      .tap(_ => eventsProducer.produce(s"piece with ID [$pieceId] deleted from game with ID [$gameId]"))

  override def movePiece(gameId: String, pieceId: Int, targetPosition: Position): Task[Unit] = {
    movePieceFlow
      .run(gameId, pieceId, targetPosition)
      .tap(_ => eventsProducer.produce(s"piece with ID [$pieceId] for game with ID [$gameId] moved to position (${targetPosition.x}, ${targetPosition.y})"))
  }
}

object GameHandlerImpl {
  lazy val live = ZLayer.derive[GameHandlerImpl]
}
