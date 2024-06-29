package http.route.impl

import http.handler.GameHandler
import http.model.{ApplicationHttpResponse, ErrorResponse, GetGameDetailsResponse}
import http.route.GameRoute
import http.route.impl.GameRouteImpl.*
import sttp.tapir.ztapir.*
import sttp.tapir.{PublicEndpoint, endpoint}
import zio.*
import sttp.tapir.json.zio.jsonBody

case class GameRouteImpl(gameHandler: GameHandler) extends GameRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = Seq(
    initGameEndpoint.zServerLogic(_ =>
      {
        for result <- gameHandler.createNewGame
          yield ()
      }.catchAllCause(err =>
        ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint))
      )
    ),
    getGameDetailsEndpoint.zServerLogic { gameId =>
      {
        for result <- gameHandler.getGameDetails(gameId)
          yield ApplicationHttpResponse(body = GetGameDetailsResponse(gameId = result))
      }.catchAllCause(err =>
        ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint))
      )
    },
    deletePieceEndpoint.zServerLogic(_ => ZIO.logInfo("DELETE a piece from the board!").unit),
    addPieceEndpoint.zServerLogic(_ => ZIO.logInfo("Add a piece to the game!").unit),
    movePieceEndpoint.zServerLogic(_ => ZIO.logInfo("Move a piece on the board!").unit)
  )
}

object GameRouteImpl {
  private val initGameEndpoint = endpoint.post
    .in("init")
    .errorOut(ErrorResponse.errorBody)
    .description("Initialize a new game")
    .tag("game play operations")


  private val getGameDetailsEndpoint = endpoint.get
    .in("init" / path[String]("gameId"))
    .out(jsonBody[ApplicationHttpResponse[GetGameDetailsResponse]])
    .errorOut(ErrorResponse.errorBody)
    .description("Retrieve details for given game")
    .tag("game play operations")
  
  val deletePieceEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.delete
    .in("piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Delete a piece from a board for given game")
    .tag("game play operations")

  val addPieceEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.post
    .in("piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Place a new piece on a board for given game")
    .tag("game play operations")

  val movePieceEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.put
    .in("piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Move a piece on a board for given game")
    .tag("game play operations")

  lazy val live = ZLayer.derive[GameRouteImpl]
}
