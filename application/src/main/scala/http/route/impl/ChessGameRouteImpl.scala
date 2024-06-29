package http.route.impl

import http.handler.ChessGameHandler
import http.model.{ApplicationHttpResponse, ErrorResponse, GetGameDetailsResponse}
import http.route.ChessGameRoute
import http.route.impl.ChessGameRouteImpl.*
import sttp.tapir.ztapir.*
import sttp.tapir.{PublicEndpoint, endpoint}
import zio.*
import sttp.tapir.json.zio.jsonBody

case class ChessGameRouteImpl(chessGameHandler: ChessGameHandler) extends ChessGameRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = Seq(
    initGameEndpoint.zServerLogic(_ =>
      {
        for result <- chessGameHandler.createNewGame
          yield ()
      }.catchAllCause(err =>
        ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint))
      )
    ),
    getGameDetailsEndpoint.zServerLogic { gameId =>
      {
        for result <- chessGameHandler.getGameDetails(gameId)
          yield ApplicationHttpResponse(body = GetGameDetailsResponse(gameId = result))
      }.catchAllCause(err =>
        ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint))
      )
    },
    deletePieceEndpoint.zServerLogic(_ => ZIO.logInfo("DELETE a chess piece from the board!").unit),
    addPieceEndpoint.zServerLogic(_ => ZIO.logInfo("Add a chess piece to the game!").unit),
    movePieceEndpoint.zServerLogic(_ => ZIO.logInfo("Move a chess piece on the board!").unit)
  )
}

object ChessGameRouteImpl {
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
  
  val deletePieceEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.delete
    .in("piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Delete a piece from a chess board for given game")
    .tag("chess game play operations")

  val addPieceEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.post
    .in("piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Place a new chess piece on a chess board for given game")
    .tag("chess game play operations")

  val movePieceEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.put
    .in("piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Move a piece on a chess board for given game")
    .tag("chess game play operations")

  lazy val live = ZLayer.derive[ChessGameRouteImpl]
}
