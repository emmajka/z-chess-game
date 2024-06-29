package repository

import model.{GameDetails, GamePiecesDetails, PieceType, PieceCoordinates}
import zio.*
trait GameRepository {
  def getGameDetails(gameId: String): IO[Exception, Seq[GameDetails]]
  def createNewGame(newGameId: String): IO[Throwable, String]
  def getGamePiecesDetails(gameId: String): IO[Exception, List[GamePiecesDetails]]
  def addNewGamePiece(gameId: String, pieceId: Int, pieceType: PieceType, coordinates: PieceCoordinates): IO[Throwable, Long]
  def deleteGamePiece(gameId: String, pieceId: Int): IO[Throwable, Long]
}
