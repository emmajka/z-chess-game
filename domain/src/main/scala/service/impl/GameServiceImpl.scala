package service.impl

import exception.GameException.GameNotExists
import model.GamePiecesDetails
import repository.GameRepository
import service.GameService
import zio.*

case class GameServiceImpl(gameRepository: GameRepository) extends GameService {
  override def getGameDetails(gameId: String): Task[Seq[GamePiecesDetails]] =
    for
      gameDetails <- gameRepository.getGameDetails(gameId = gameId)
      _ <- ZIO.fail(GameNotExists(gameId = gameId)).when(gameDetails.isEmpty)
      gamePiecesDetails <- gameRepository.getGamePiecesDetails(gameId = gameId)
    yield gamePiecesDetails
}

object GameServiceImpl {
  lazy val live = ZLayer.derive[GameServiceImpl]
}
