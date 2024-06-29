package repository

import model.{GameDetails, GamePiecesDetails, PieceType, PieceCoordinates}
import zio.*
trait GameRepository {
  def getGameDetails(gameId: String): IO[Exception, Seq[GameDetails]]
  def createNewGame(newGameId: String): IO[Exception, Long]
  def getGamePiecesDetails(gameId: String): IO[Exception, Seq[GamePiecesDetails]]
  def addNewGamePiece(gameId: String, pieceId: Int, pieceType: PieceType, coordinates: PieceCoordinates): IO[Exception, Long]
  def deleteGamePiece(gameId: String, pieceId: Int): IO[Exception, Long]
}
