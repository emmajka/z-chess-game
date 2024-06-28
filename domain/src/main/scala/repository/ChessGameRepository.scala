package repository

import model.{ChessGameDetails, ChessGamePiecesDetails, ChessPieceType, PieceCoordinates}
import zio.*
trait ChessGameRepository {
  def getChessGameDetails(gameId: String): IO[Exception, ChessGameDetails]
  def initGameOfChess(newGameId: String): IO[Exception, Long]
  def getChessGamePiecesDetails(gameId: String): IO[Exception, Seq[ChessGamePiecesDetails]]
  def createNewChessGamePiece(gameId: String, pieceId: Int, pieceType: ChessPieceType, coordinates: PieceCoordinates): IO[Exception, Long]
  def deleteChessPiece(gameId: String, pieceId: Int): IO[Exception, Long]
}
