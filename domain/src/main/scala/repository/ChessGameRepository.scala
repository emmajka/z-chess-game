package repository

import model.{ChessBoardType, ChessGameDetails, ChessPieceType}
import zio.*
trait ChessGameRepository {
  def getChessGameDetails(gameId: String): IO[Exception, Seq[ChessGameDetails]]
  def initGameOfChess(newGameId: String, chessBoardType: ChessBoardType): IO[Exception, Long]
}
