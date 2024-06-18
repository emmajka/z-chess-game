package http.route.impl

import http.model.ErrorResponse
import http.route.ChessGameRoute
import http.route.impl.ChessGameRouteImpl.*
import sttp.tapir.ztapir.*
import sttp.tapir.{PublicEndpoint, endpoint}
import zio.*

case class ChessGameRouteImpl() extends ChessGameRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = Seq(
    deletePieceEndpoint.zServerLogic(_ => ZIO.logInfo("DELETE a chess piece from the board!") *> ZIO.unit),
    addPieceEndpoint.zServerLogic(_ => ZIO.logInfo("Add a chess piece to the game!") *> ZIO.unit),
    movePieceEndpoint.zServerLogic(_ => ZIO.logInfo("Move a chess piece on the board!") *> ZIO.unit),
    getGameStateEndpoint.zServerLogic(_ => ZIO.logInfo("Get chess game state!") *> ZIO.unit),
    initGameEndpoint.zServerLogic(_ => ZIO.logInfo("Initialize a new chess game!") *> ZIO.unit),
  )
}

object ChessGameRouteImpl {
  val deletePieceEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.delete
    .in("piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Delete a piece from a chess board for given game")

  val addPieceEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.post
    .in("piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Place a new chess piece on a chess board for given game")
  val movePieceEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.put
    .in("piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Move a piece on a chess board for given game")
  val getGameStateEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.get
    .in("state")
    .errorOut(ErrorResponse.errorBody)
    .description("Retrieve given game state")
  val initGameEndpoint: PublicEndpoint[Unit, ErrorResponse, Unit, Any] = endpoint.post
    .in("init")
    .errorOut(ErrorResponse.errorBody)
    .description("Initialize a new game")

  lazy val layer = ZLayer.derive[ChessGameRouteImpl]
}
