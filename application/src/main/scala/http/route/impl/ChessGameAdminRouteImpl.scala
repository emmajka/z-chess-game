package http.route.impl

import http.model.ErrorResponse
import http.route.impl.ChessGameAdminRouteImpl.*
import http.route.{ChessGameAdminRoute, ChessGameRoute}
import sttp.tapir.ztapir.*
import sttp.tapir.{PublicEndpoint, endpoint}
import zio.*

case class ChessGameAdminRouteImpl() extends ChessGameAdminRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = Seq(
    initGameEndpoint.zServerLogic(_ => ZIO.logInfo("Initialize a new chess game!") *> ZIO.unit)
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
