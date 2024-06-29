package flow.impl

import flow.CreateNewGameFlow
import repository.GameRepository
import service.GameIdGenerator
import zio.*

case class CreateNewGameFlowImpl(idGenerator: GameIdGenerator, gameRepository: GameRepository) extends CreateNewGameFlow {
  override def run(): Task[Unit] =
    val gameId = idGenerator.generate
    for _ <- ZIO.logInfo(s"generated game ID: $gameId")
      _ <- gameRepository.createNewGame(gameId)
    yield()
}

object CreateNewGameFlowImpl {
  lazy val live = ZLayer.derive[CreateNewGameFlowImpl]
}
