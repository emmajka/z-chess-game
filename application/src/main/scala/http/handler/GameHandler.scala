package http.handler

import flow.impl.GetGameDetailsResult
import model.{PieceCoordinates, PieceType}
import zio.Task

trait GameHandler {
  def createNewGame: Task[String]
  def getGameDetails(gameId: String): Task[GetGameDetailsResult]
  def addPiece(gameId: String, pieceType: PieceType, targetCoordinates: PieceCoordinates): Task[Int]
}
