package repository

import zio.Task

trait ChessGamePiecesRepository {
  def getChessGameActivePiecesDetails(gameId: String): Task[Unit]
}
