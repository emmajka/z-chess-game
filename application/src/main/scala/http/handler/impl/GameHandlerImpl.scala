package http.handler.impl

import flow.impl.GetGameDetailsResult
import flow.{AddPieceFlow, CreateNewGameFlow, GetGameDetailsFlow}
import http.handler.GameHandler
import model.{PieceCoordinates, PieceType}
import zio.{Task, ZLayer}

case class GameHandlerImpl(addPieceFlow: AddPieceFlow, createNewGameFlow: CreateNewGameFlow, getGameDetailsFlow: GetGameDetailsFlow) extends GameHandler {
  override def createNewGame: Task[String] = createNewGameFlow.run()
  override def getGameDetails(gameId: String): Task[GetGameDetailsResult] = getGameDetailsFlow.run(gameId)
  override def addPiece(gameId: String, pieceType: PieceType, targetCoordinates: PieceCoordinates): Task[Int] =
    addPieceFlow.run(gameId, pieceType, targetCoordinates)
}

object GameHandlerImpl {
  lazy val live = ZLayer.derive[GameHandlerImpl]
}
