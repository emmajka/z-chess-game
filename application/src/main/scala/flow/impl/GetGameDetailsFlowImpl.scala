package flow.impl

import exception.GameException.GameNotExists
import flow.GetGameDetailsFlow
import model.GamePiecesDetails
import repository.GameRepository
import zio.{Task, ZIO, ZLayer}

case class GetGameDetailsFlowImpl(gameRepository: GameRepository) extends GetGameDetailsFlow {
  override def run(gameId: String): Task[GetGameDetailsResult] =
    for
      gameDetails <- gameRepository.getGameDetails(gameId = gameId).filterOrFail(_.nonEmpty)(GameNotExists(gameId = gameId))
      pieces <- gameRepository.getGamePiecesDetails(gameId = gameId)
    yield GetGameDetailsResult(gameId = gameId, pieces = pieces)

}

case class GetGameDetailsResult(gameId: String, pieces: Seq[GamePiecesDetails])

object GetGameDetailsFlowImpl {
  lazy val live = ZLayer.derive[GetGameDetailsFlowImpl]
}
