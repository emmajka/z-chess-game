package service

import model.GamePiecesDetails
import zio.Task

trait GameService {
  def getGameDetails(gameId: String): Task[Seq[GamePiecesDetails]]
}
