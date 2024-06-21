package repository

import model.ChessGameDetails
import zio.*
trait ChessGameRepository {
  def getChessGameDetails(gameId: String): IO[Exception, Seq[ChessGameDetails]]
  def initGameOfChess(newGameId: String): IO[Exception, Long]
}
