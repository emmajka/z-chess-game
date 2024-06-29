package http.handler.impl

import flow.impl.GetGameDetailsResult
import flow.{AddPieceFlow, CreateNewGameFlow, DeletePieceFlow, GetGameDetailsFlow}
import http.handler.GameHandler
import model.{PieceCoordinates, PieceType}
import zio.{Task, ZLayer}

case class GameHandlerImpl(
  addPieceFlow: AddPieceFlow,
  createNewGameFlow: CreateNewGameFlow,
  getGameDetailsFlow: GetGameDetailsFlow,
  deletePieceFlow: DeletePieceFlow)
    extends GameHandler {
  override def createNewGame: Task[String] = createNewGameFlow.run()
  
  override def getGameDetails(gameId: String): Task[GetGameDetailsResult] = getGameDetailsFlow.run(gameId = gameId)
  
  override def addPiece(gameId: String, pieceType: PieceType, targetCoordinates: PieceCoordinates): Task[Int] =
    addPieceFlow.run(gameId = gameId, pieceType = pieceType, pieceCoordinates = targetCoordinates)

  override def deletePiece(gameId: String, pieceId: Int): Task[Unit] = deletePieceFlow.run(gameId = gameId, pieceId = pieceId)
}

object GameHandlerImpl {
  lazy val live = ZLayer.derive[GameHandlerImpl]
}
