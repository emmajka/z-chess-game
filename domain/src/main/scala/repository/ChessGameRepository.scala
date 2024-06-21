package repository

import model.ChessGameDetails
import zio.*
trait ChessGameRepository {
  def getChessGameDetails(gameId: String): IO[Exception, List[ChessGameDetails]]
  def initGameOfChess(newGameId: String): IO[Exception, ChessGameDetails]
}
