package service.impl

import exception.ChessGameException.ChessGameNotExists
import model.ChessGamePiecesDetails
import repository.ChessGameRepository
import service.ChessGameService
import zio.*

case class ChessGameServiceImpl(chessGameRepository: ChessGameRepository) extends ChessGameService {
  override def getGameDetails(gameId: String): Task[Seq[ChessGamePiecesDetails]] =
    for
      chessGameDetails <- chessGameRepository.getChessGameDetails(gameId = gameId)
      _ <- ZIO.fail(ChessGameNotExists(gameId = gameId)).when(chessGameDetails.isEmpty)
      chessGamePiecesDetails <- chessGameRepository.getChessGamePiecesDetails(gameId = gameId)
    yield chessGamePiecesDetails
}

object ChessGameServiceImpl {
  lazy val live = ZLayer.derive[ChessGameServiceImpl]
}
