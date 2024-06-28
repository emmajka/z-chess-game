package flow.impl

import flow.GameInitializationFlow
import model.ChessBoardType.Standard
import repository.ChessGameRepository
import service.IdGenerator
import zio.*

case class GameInitializationFlowImpl(idGenerator: IdGenerator, chessGameRepository: ChessGameRepository) extends GameInitializationFlow {
  override def run(): Task[Unit] =
    val gameId = idGenerator.generate
    for _ <- ZIO.logInfo(s"generated game ID: $gameId")
      _ <- chessGameRepository.initGameOfChess(gameId, Standard)
    yield()
}

object GameInitializationFlowImpl {
  lazy val live = ZLayer.derive[GameInitializationFlowImpl]
}
