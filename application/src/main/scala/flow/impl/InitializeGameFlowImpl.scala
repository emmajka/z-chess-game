package flow.impl

import flow.InitializeGameFlow
import repository.ChessGameRepository
import service.GameIdGenerator
import zio.*

case class InitializeGameFlowImpl(idGenerator: GameIdGenerator, chessGameRepository: ChessGameRepository) extends InitializeGameFlow {
  override def run(): Task[Unit] =
    val gameId = idGenerator.generate
    for _ <- ZIO.logInfo(s"generated game ID: $gameId")
      _ <- chessGameRepository.initGameOfChess(gameId)
    yield()
}

object InitializeGameFlowImpl {
  lazy val live = ZLayer.derive[InitializeGameFlowImpl]
}
