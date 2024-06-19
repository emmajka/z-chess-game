package http.route.impl

import http.handler.ChessGameAdminHandler
import http.model.ErrorResponse
import http.route.impl.ChessGameAdminRouteImpl.*
import http.route.{ChessGameAdminRoute, ChessGameRoute}
import sttp.tapir.ztapir.*
import sttp.tapir.{PublicEndpoint, endpoint}
import zio.*

case class ChessGameAdminRouteImpl(chessGameAdminHandler: ChessGameAdminHandler) extends ChessGameAdminRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = Seq(
    initGameEndpoint.zServerLogic(_ =>
      chessGameAdminHandler.initGame
        .catchAll(err => ZIO.logError(s"Error! $err") *> ZIO.fail(ErrorResponse(message = err.getMessage)))
    )
  )
}

object ChessGameAdminRouteImpl {

  val initGameEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.post
    .in("init")
    .errorOut(ErrorResponse.errorBody)
    .description("Initialize a new game")
    .tag("chess game administration operations")

  lazy val live = ZLayer.derive[ChessGameAdminRouteImpl]
}
