package flow.impl

import flow.GetGameDetailsFlow
import repository.GameRepository
import zio.{Task, ZIO, ZLayer}

case class GetGameDetailsFlowImpl(gameRepository: GameRepository) extends GetGameDetailsFlow {
  override def run(gameId: String): Task[String] =
    for
      _ <- ZIO.logInfo(s"retrieval of game details for game with ID $gameId")
      gameDetails <- gameRepository.getGameDetails(gameId = gameId)
    yield "random for time being" // TODO error handling
}

object GetGameDetailsFlowImpl {
  lazy val live = ZLayer.derive[GetGameDetailsFlowImpl]
}
