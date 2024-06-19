package http.handler.impl

import http.handler.ChessGameAdminHandler
import zio.{ZIO, ZLayer}

case class ChessGameAdminHandlerImpl() extends ChessGameAdminHandler {
  override def initGame: ZIO[Any, Exception, Unit] = ZIO.logInfo("handling game initialization!") *> ZIO.unit
}

object ChessGameAdminHandlerImpl {
  lazy val live: ZLayer[Any, Any, ChessGameAdminHandlerImpl] = ZLayer.derive[ChessGameAdminHandlerImpl]
}
