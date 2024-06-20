package http.handler.impl

import http.handler.ChessGameAdminHandler
import zio.{Task, ZIO, ZLayer}

case class ChessGameAdminHandlerImpl() extends ChessGameAdminHandler {
  override def initGame: Task[Unit] = ZIO.logInfo("handling game initialization!") *> ZIO.unit

  override def getGameDetails(gameId: String): Task[String] =
    ZIO.logInfo(s"retrieval of game details for game with ID $gameId") *> ZIO.succeed("some details")
}

object ChessGameAdminHandlerImpl {
  lazy val live: ZLayer[Any, Any, ChessGameAdminHandlerImpl] = ZLayer.derive[ChessGameAdminHandlerImpl]
}
