package http.handler

import zio.ZIO

trait ChessGameAdminHandler {
  def initGame: ZIO[Any, Exception, Unit]
}
