package flow.impl

import flow.CreateNewGameFlow
import repository.GameRepository
import service.GameIdGenerator
import zio.*

case class CreateNewGameFlowImpl(idGenerator: GameIdGenerator, gameRepository: GameRepository) extends CreateNewGameFlow {
  override def run(): Task[String] =
    val gameId = idGenerator.generate
    for returnedGameId <- gameRepository.createNewGame(gameId)
    yield returnedGameId
}

object CreateNewGameFlowImpl {
  lazy val live = ZLayer.derive[CreateNewGameFlowImpl]
}
