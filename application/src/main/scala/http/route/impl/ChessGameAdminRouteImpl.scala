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
      {
        for result <- chessGameAdminHandler.initGame
        yield ()
      }.catchAllCause(err =>
        ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint))
      )
    ),
    getGameDetailsEndpoint.zServerLogic { gameId =>
      {
        for result <- chessGameAdminHandler.getGameDetails(gameId)
        yield ApplicationHttpResponse(body = GetGameDetailsResponse(gameId = result))
      }.catchAllCause(err =>
        ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint))
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
