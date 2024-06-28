package service

import model.ChessGamePiecesDetails
import zio.Task

trait ChessGameService {
  def getGameDetails(gameId: String): Task[Seq[ChessGamePiecesDetails]]
}
