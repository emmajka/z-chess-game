package http.handler

import zio.{Task, ZIO}

trait ChessGameAdminHandler {
  def initGame: Task[Unit]
  def getGameDetails(gameId: String): Task[String]
}
