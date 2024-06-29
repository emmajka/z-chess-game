package flow.impl

import flow.GetGameDetailsFlow
import repository.ChessGameRepository
import zio.{Task, ZIO, ZLayer}

case class GetGameDetailsFlowImpl(chessGameRepository: ChessGameRepository) extends GetGameDetailsFlow {
  override def run(gameId: String): Task[String] =
    for
      _ <- ZIO.logInfo(s"retrieval of game details for game with ID $gameId")
      gameDetails <- chessGameRepository.getChessGameDetails(gameId = gameId)
    yield "random for time being" // TODO error handling
}

object GetGameDetailsFlowImpl {
  lazy val live = ZLayer.derive[GetGameDetailsFlowImpl]
}
