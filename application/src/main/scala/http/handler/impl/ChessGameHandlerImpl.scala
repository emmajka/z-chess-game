package http.handler.impl

import http.handler.ChessGameHandler
import model.{ChessPieceType, PieceCoordinates}
import zio.{Task, ZLayer}

case class ChessGameHandlerImpl () extends ChessGameHandler {
  override def addPiece(gameId: String, pieceType: ChessPieceType, pieceCoordinates: PieceCoordinates): Task[String] = ???
}

object ChessGameHandlerImpl{
  lazy val live = ZLayer.derive[ChessGameHandlerImpl]
}
