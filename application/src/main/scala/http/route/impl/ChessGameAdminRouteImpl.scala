package http.route.impl

import http.handler.ChessGameAdminHandler
import http.model.{ApplicationHttpResponse, ErrorResponse, GetGameDetailsResponse}
import http.route.ChessGameAdminRoute
import http.route.impl.ChessGameAdminRouteImpl.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import zio.*

case class ChessGameAdminRouteImpl(chessGameAdminHandler: ChessGameAdminHandler) extends ChessGameAdminRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = Seq(
    initGameEndpoint.zServerLogic(_ =>
      chessGameAdminHandler.initGame
        .catchAll(err => ZIO.logError(s"Error! $err") *> ZIO.fail(ErrorResponse(message = err.getMessage)))
    ),
    getGameDetailsEndpoint.zServerLogic { gameId =>
      (for result <- chessGameAdminHandler.getGameDetails(gameId)
      yield ApplicationHttpResponse(body = GetGameDetailsResponse(gameId = result))).catchAll(err =>
        ZIO.logError(s"Error! $err") *> ZIO.fail(ErrorResponse(message = err.getMessage))
      )
    }
  )
}

object ChessGameAdminRouteImpl {

  private val initGameEndpoint = endpoint.post
    .in("init")
    .errorOut(ErrorResponse.errorBody)
    .description("Initialize a new game")
    .tag("chess game administration operations")

  private val getGameDetailsEndpoint = endpoint.get
    .in("init" / path[String]("gameId"))
    .out(jsonBody[ApplicationHttpResponse[GetGameDetailsResponse]])
    .errorOut(ErrorResponse.errorBody)
    .description("Initialize a new game")
    .tag("chess game administration operations")

  lazy val live = ZLayer.derive[ChessGameAdminRouteImpl]
}
