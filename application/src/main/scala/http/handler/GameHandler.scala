package http.handler

import flow.impl.GetGameDetailsResult
import model.{Position, PieceType}
import zio.Task

trait GameHandler {
  def createNewGame: Task[String]
  def getGameDetails(gameId: String): Task[GetGameDetailsResult]
  def addPiece(gameId: String, pieceType: PieceType, targetPosition: Position): Task[Int]
  def deletePiece(gameId: String, pieceId: Int): Task[Unit]
  def movePiece(gameId: String, pieceId: Int, targetPosition: Position): Task[Unit]
}
