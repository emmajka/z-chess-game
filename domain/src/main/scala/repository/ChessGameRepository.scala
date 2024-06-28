package repository

import model.{ChessGameDetails, ChessGamePiecesDetails}
import zio.*
trait ChessGameRepository {
  def getChessGameDetails(gameId: String): IO[Exception, Seq[ChessGameDetails]]
  def initGameOfChess(newGameId: String): IO[Exception, Long]
  def getChessGamePiecesDetails(gameId: String): IO[Exception, Seq[ChessGamePiecesDetails]]
}
