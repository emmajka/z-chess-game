package http.handler.impl

import flow.AddPieceFlow
import http.handler.ChessGameHandler
import model.{ChessPieceType, PieceCoordinates}
import zio.{Task, ZLayer}

case class ChessGameHandlerImpl(addChessPieceFlow: AddPieceFlow) extends ChessGameHandler {
  override def addPiece(gameId: String, pieceType: ChessPieceType, pieceCoordinates: PieceCoordinates): Task[String] =
    for _ <- addChessPieceFlow.run(gameId, pieceType, pieceCoordinates)
    yield "Created"
}

object ChessGameHandlerImpl {
  lazy val live = ZLayer.derive[ChessGameHandlerImpl]
}
