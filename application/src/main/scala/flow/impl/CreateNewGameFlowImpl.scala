package flow.impl

import flow.CreateNewGameFlow
import repository.GameRepository
import service.GameIdGenerator
import zio.*

case class CreateNewGameFlowImpl(idGenerator: GameIdGenerator, gameRepository: GameRepository) extends CreateNewGameFlow {
  override def run(): Task[String] =
    val gameId = idGenerator.generate
    for _ <- ZIO.logInfo(s"generated game ID: $gameId")
      r <- gameRepository.createNewGame(gameId)
      _ <- ZIO.logInfo(s"created rows: $r")
    yield gameId
}

object CreateNewGameFlowImpl {
  lazy val live = ZLayer.derive[CreateNewGameFlowImpl]
}
