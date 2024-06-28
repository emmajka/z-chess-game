package flow.impl

import flow.RetrieveGameDetailsFlow
import repository.ChessGameRepository
import zio.{Task, ZIO, ZLayer}

case class RetrieveGameDetailsFlowImpl(chessGameRepository: ChessGameRepository) extends RetrieveGameDetailsFlow {
  override def run(gameId: String): Task[String] =
    for
      _ <- ZIO.logInfo(s"retrieval of game details for game with ID $gameId")
      gameDetails <- chessGameRepository.getChessGameDetails(gameId = gameId)
    yield gameDetails.gameId // TODO error handling
}

object RetrieveGameDetailsFlowImpl {
  lazy val live = ZLayer.derive[RetrieveGameDetailsFlowImpl]
}
