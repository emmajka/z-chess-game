package http.handler.impl

import flow.*
import flow.impl.GetGameDetailsResult
import http.handler.GameHandler
import model.{PieceType, Position}
import zio.{Task, ZLayer}

case class GameHandlerImpl(
  addPieceFlow: AddPieceFlow,
  createNewGameFlow: CreateNewGameFlow,
  getGameDetailsFlow: GetGameDetailsFlow,
  deletePieceFlow: DeletePieceFlow,
  movePieceFlow: MovePieceFlow)
    extends GameHandler {
  override def createNewGame: Task[String] = createNewGameFlow.run()

  override def getGameDetails(gameId: String): Task[GetGameDetailsResult] = getGameDetailsFlow.run(gameId = gameId)

  override def addPiece(gameId: String, pieceType: PieceType, targetPosition: Position): Task[Int] =
    addPieceFlow.run(gameId = gameId, pieceType = pieceType, pieceCoordinates = targetPosition)

  override def deletePiece(gameId: String, pieceId: Int): Task[Unit] = deletePieceFlow.run(gameId = gameId, pieceId = pieceId)

  override def movePiece(gameId: String, pieceId: Int, targetPosition: Position): Task[Unit] = {
    movePieceFlow.run(gameId, pieceId, targetPosition)
  }
}

object GameHandlerImpl {
  lazy val live = ZLayer.derive[GameHandlerImpl]
}
