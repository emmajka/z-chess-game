package http.handler.impl

import flow.{AddPieceFlow, CreateNewGameFlow, GetGameDetailsFlow}
import http.handler.GameHandler
import model.{PieceType, PieceCoordinates}
import zio.{Task, ZLayer}

case class GameHandlerImpl(addPieceFlow: AddPieceFlow, createNewGameFlow: CreateNewGameFlow, getGameDetailsFlow: GetGameDetailsFlow)
    extends GameHandler {
  override def createNewGame: Task[Unit] = createNewGameFlow.run()
  override def getGameDetails(gameId: String): Task[String] = getGameDetailsFlow.run(gameId)
  override def addPiece(gameId: String, pieceType: PieceType, pieceCoordinates: PieceCoordinates): Task[String] =
    for _ <- addPieceFlow.run(gameId, pieceType, pieceCoordinates)
    yield "Created"
}

object GameHandlerImpl {
  lazy val live = ZLayer.derive[GameHandlerImpl]
}
