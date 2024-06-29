package flow.impl

import flow.CreateNewGameFlow
import repository.ChessGameRepository
import service.GameIdGenerator
import zio.*

case class CreateNewGameFlowImpl(idGenerator: GameIdGenerator, chessGameRepository: ChessGameRepository) extends CreateNewGameFlow {
  override def run(): Task[Unit] =
    val gameId = idGenerator.generate
    for _ <- ZIO.logInfo(s"generated game ID: $gameId")
      _ <- chessGameRepository.initGameOfChess(gameId)
    yield()
}

object CreateNewGameFlowImpl {
  lazy val live = ZLayer.derive[CreateNewGameFlowImpl]
}
