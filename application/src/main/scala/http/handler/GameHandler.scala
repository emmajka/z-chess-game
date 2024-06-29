package http.handler

import model.{PieceType, PieceCoordinates}
import zio.Task

trait GameHandler {
  def createNewGame: Task[Unit]
  def getGameDetails(gameId: String): Task[String]
  def addPiece(gameId: String, pieceType: PieceType, pieceCoordinates: PieceCoordinates): Task[String]
}
