package repository.impl

import repository.ChessGamePiecesRepository
import zio.Task

case class ChessGamePiecesRepositoryImpl () extends ChessGamePiecesRepository {
  override def getChessGameActivePiecesDetails(gameId: String): Task[Unit] = ???
}
