package http.route.impl

import http.handler.GameHandler
import http.request.AddPieceRequest
import http.response.{ApplicationHttpResponse, ErrorResponse, GetGameDetailsResponse}
import http.route.GameRoute
import http.route.impl.GameRouteImpl.*
import sttp.tapir.endpoint
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import zio.*

case class GameRouteImpl(gameHandler: GameHandler) extends GameRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = Seq(
    initGameEndpoint.zServerLogic(
      _ =>
        {
          for result <- gameHandler.createNewGame
          yield ApplicationHttpResponse(body = result)
        }.catchAllCause(err => ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint)))
    ),
    getGameDetailsEndpoint.zServerLogic {
      gameId =>
        {
          for
            result <- gameHandler.getGameDetails(gameId)
            piecesDesc = result
              .pieces
              .filter(_.active)
              .map(p => s"${p.pieceType.code}${p.pieceId}: ${p.xCoordinate}:${p.yCoordinate}")
          yield ApplicationHttpResponse(body = GetGameDetailsResponse(gameId = result.gameId, pieces = piecesDesc))
        }.catchAllCause(err => ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint)))
    },
    deletePieceEndpoint.zServerLogic(_ => ZIO.logInfo("DELETE a piece from the board!").unit),
    addPieceEndpoint.zServerLogic(
      req =>
        {
          for result <- gameHandler.addPiece(gameId = req.gameId, pieceType = req.pieceType, targetCoordinates = req.targetCoordinates) yield ()
        }.catchAllCause(err => ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint)))
    ),
    movePieceEndpoint.zServerLogic(_ => ZIO.logInfo("Move a piece on the board!").unit)
  )
}

object GameRouteImpl {
  private val initGameEndpoint = endpoint
    .post
    .in("game")
    .out(jsonBody[ApplicationHttpResponse[String]])
    .errorOut(ErrorResponse.errorBody)
    .description("Initialize a new game")
    .tag("game play operations")

  private val getGameDetailsEndpoint = endpoint
    .get
    .in("game" / path[String]("gameId"))
    .out(jsonBody[ApplicationHttpResponse[GetGameDetailsResponse]])
    .errorOut(ErrorResponse.errorBody)
    .description("Retrieve details for given game")
    .tag("game play operations")

  private val deletePieceEndpoint = endpoint
    .delete
    .in("game" / "piece")
    .in(path[String]("gameId"))
    .in(path[Int]("pieceId"))
    .errorOut(ErrorResponse.errorBody)
    .description("Delete a piece from a board for given game")
    .tag("game play operations")

  private val addPieceEndpoint = endpoint
    .post
    .in("game" / "piece")
    .in(jsonBody[AddPieceRequest])
    .errorOut(ErrorResponse.errorBody)
    .description("Place a new piece on a board for given game")
    .tag("game play operations")

  private val movePieceEndpoint = endpoint
    .put
    .in("game" / "piece")
    .errorOut(ErrorResponse.errorBody)
    .description("Move a piece on a board for given game")
    .tag("game play operations")

  lazy val live = ZLayer.derive[GameRouteImpl]
}
