package http.handler

import model.{ChessPieceType, PieceCoordinates}
import zio.Task

trait ChessGameHandler {
  def addPiece(gameId: String, pieceType: ChessPieceType, pieceCoordinates: PieceCoordinates): Task[String]
}
