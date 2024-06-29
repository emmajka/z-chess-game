package flow.impl

import flow.CreateNewGameFlow
import repository.GameRepository
import service.GameIdGenerator
import zio.*

case class CreateNewGameFlowImpl(idGenerator: GameIdGenerator, gameRepository: GameRepository) extends CreateNewGameFlow {
  override def run(): Task[String] =
    val gameId = idGenerator.generate
    gameRepository.createNewGame(gameId).as(gameId)
}

object CreateNewGameFlowImpl {
  lazy val live = ZLayer.derive[CreateNewGameFlowImpl]
}
