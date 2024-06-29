package http.handler.impl

import flow.{AddPieceFlow, CreateNewGameFlow, GetGameDetailsFlow}
import http.handler.ChessGameHandler
import model.{ChessPieceType, PieceCoordinates}
import zio.{Task, ZLayer}

case class ChessGameHandlerImpl(addChessPieceFlow: AddPieceFlow, createNewGameFlow: CreateNewGameFlow, getGameDetailsFlow: GetGameDetailsFlow)
    extends ChessGameHandler {
  override def createNewGame: Task[Unit] = createNewGameFlow.run()
  override def getGameDetails(gameId: String): Task[String] = getGameDetailsFlow.run(gameId)
  override def addPiece(gameId: String, pieceType: ChessPieceType, pieceCoordinates: PieceCoordinates): Task[String] =
    for _ <- addChessPieceFlow.run(gameId, pieceType, pieceCoordinates)
    yield "Created"
}

object ChessGameHandlerImpl {
  lazy val live = ZLayer.derive[ChessGameHandlerImpl]
}
