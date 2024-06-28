package http.handler.impl

import flow.{InitializeGameFlow, RetrieveGameDetailsFlow}
import http.handler.ChessGameAdminHandler
import zio.{Task, ZIO, ZLayer}

case class ChessGameAdminHandlerImpl(
  retrieveGameDetailsFlow: RetrieveGameDetailsFlow,
  gameInitializationFlow: InitializeGameFlow
) extends ChessGameAdminHandler {
  override def initGame: Task[Unit] = gameInitializationFlow.run()

  override def getGameDetails(gameId: String): Task[String] = retrieveGameDetailsFlow.run(gameId)
}

object ChessGameAdminHandlerImpl {
  lazy val live = ZLayer.derive[ChessGameAdminHandlerImpl]
}
