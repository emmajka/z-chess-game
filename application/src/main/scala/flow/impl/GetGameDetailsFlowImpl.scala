package flow.impl

import exception.GameException.GameNotExists
import flow.GetGameDetailsFlow
import model.GamePiecesDetails
import repository.GameRepository
import zio.{Task, ZIO, ZLayer}

case class GetGameDetailsFlowImpl(gameRepository: GameRepository) extends GetGameDetailsFlow {
  override def run(gameId: String): Task[GetGameDetailsResult] =
    for
      _ <- ZIO.logInfo(s"retrieval of game details for game with ID $gameId")
      gameDetails <- gameRepository.getGameDetails(gameId = gameId)
      _ <- ZIO.fail(GameNotExists(gameId = gameId)).when(gameDetails.isEmpty)
      pieces <- gameRepository.getGamePiecesDetails(gameId = gameId)
      _ <- ZIO.logInfo(s"pieces: ${pieces.size}")
    yield GetGameDetailsResult(gameId = gameId, pieces = pieces)

}

case class GetGameDetailsResult(gameId: String, pieces: Seq[GamePiecesDetails])

object GetGameDetailsFlowImpl {
  lazy val live = ZLayer.derive[GetGameDetailsFlowImpl]
}
