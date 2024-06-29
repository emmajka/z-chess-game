package http.handler

import model.{ChessPieceType, PieceCoordinates}
import zio.Task

trait ChessGameHandler {
  def createNewGame: Task[Unit]
  def getGameDetails(gameId: String): Task[String]
  def addPiece(gameId: String, pieceType: ChessPieceType, pieceCoordinates: PieceCoordinates): Task[String]
}
