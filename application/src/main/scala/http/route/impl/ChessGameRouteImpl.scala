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
    movePieceEndpoint.zServerLogic(_ => ZIO.logInfo("Move a chess piece on the board!") *> ZIO.unit)
  )
}

object ChessGameRouteImpl {
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
