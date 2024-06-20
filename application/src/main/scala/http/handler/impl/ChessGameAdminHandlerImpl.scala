package http.handler.impl

import http.handler.ChessGameAdminHandler
import repository.ChessGameRepository
import zio.{Task, ZIO, ZLayer}

case class ChessGameAdminHandlerImpl(chessGameRepository: ChessGameRepository) extends ChessGameAdminHandler {
  override def initGame: Task[Unit] = ZIO.logInfo("handling game initialization!") *> ZIO.unit

  override def getGameDetails(gameId: String): Task[String] =
    for _       <- ZIO.logInfo(s"retrieval of game details for game with ID $gameId")
    gameDetails <- chessGameRepository.getChessGameDetails(gameId = gameId)
    yield gameDetails.headOption.map(_.gameId).getOrElse("Game not found!!!")
}

object ChessGameAdminHandlerImpl {
  lazy val live = ZLayer.derive[ChessGameAdminHandlerImpl]
}
