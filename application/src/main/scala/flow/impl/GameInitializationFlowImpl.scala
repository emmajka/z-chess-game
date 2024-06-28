package flow.impl

import flow.GameInitializationFlow
import repository.ChessGameRepository
import service.GameIdGenerator
import zio.*

case class GameInitializationFlowImpl(idGenerator: GameIdGenerator, chessGameRepository: ChessGameRepository) extends GameInitializationFlow {
  override def run(): Task[Unit] =
    val gameId = idGenerator.generate
    for _ <- ZIO.logInfo(s"generated game ID: $gameId")
      _ <- chessGameRepository.initGameOfChess(gameId)
    yield()
}

object GameInitializationFlowImpl {
  lazy val live = ZLayer.derive[GameInitializationFlowImpl]
}
