package http.route.impl

import http.handler.GameHandler
import http.request.{AddPieceRequest, MovePieceRequest}
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
    deletePieceEndpoint.zServerLogic {
      case (gameId: String, pieceId: Int) =>
        {
          for _ <- gameHandler.deletePiece(gameId = gameId, pieceId = pieceId)
          yield ApplicationHttpResponse(body = s"Successfully deletes piece with ID [$pieceId] from game with ID [$gameId]")
        }.catchAllCause(err => ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint)))
    },
    addPieceEndpoint.zServerLogic {
      case (gameId: String, req: AddPieceRequest) =>
        {
          for result <- gameHandler.addPiece(gameId = gameId, pieceType = req.pieceType, targetPosition = req.targetPosition)
          yield ApplicationHttpResponse(body = s"Successfully added new piece to game with ID [$gameId], new piece's ID is $result")
        }.catchAllCause(err => ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint)))
    },
    movePieceEndpoint.zServerLogic {
      case (gameId: String, pieceId: Int, req: MovePieceRequest) =>
        {
          for _ <- gameHandler.movePiece(gameId = gameId, pieceId = pieceId, targetPosition = req.targetPosition)
          yield ApplicationHttpResponse(body = s"Successfully moved a poiece")
        }.catchAllCause(err => ZIO.logError(s"Failure! ${err.prettyPrint}") *> ZIO.fail(ErrorResponse(message = err.prettyPrint)))
    }
  )
}

object GameRouteImpl {
  private val initGameEndpoint = endpoint
    .post
    .in("game")
    .out(jsonBody[ApplicationHttpResponse[String]])
    .errorOut(ErrorResponse.errorBody)
    .description("Initialize a new game")
    .tag("game operations")

  private val getGameDetailsEndpoint = endpoint
    .get
    .in("game" / path[String]("gameId"))
    .out(jsonBody[ApplicationHttpResponse[GetGameDetailsResponse]])
    .errorOut(ErrorResponse.errorBody)
    .description("Get game details")
    .tag("game operations")

  private val deletePieceEndpoint = endpoint
    .delete
    .in("game" / "piece")
    .in(path[String]("gameId"))
    .in(path[Int]("pieceId"))
    .out(jsonBody[ApplicationHttpResponse[String]])
    .errorOut(ErrorResponse.errorBody)
    .description("Delete game piece")
    .tag("game operations")

  private val addPieceEndpoint = endpoint
    .post
    .in("game" / "piece")
    .in(path[String]("gameId"))
    .in(jsonBody[AddPieceRequest])
    .out(jsonBody[ApplicationHttpResponse[String]])
    .errorOut(ErrorResponse.errorBody)
    .description("Add new piece to an existing game")
    .tag("game operations")

  private val movePieceEndpoint = endpoint
    .put
    .in("game" / "piece")
    .in(path[String]("gameId"))
    .in(path[Int]("pieceId"))
    .in(jsonBody[MovePieceRequest])
    .out(jsonBody[ApplicationHttpResponse[String]])
    .errorOut(ErrorResponse.errorBody)
    .description("Move a piece in an existing game")
    .tag("game operations")

  lazy val live = ZLayer.derive[GameRouteImpl]
}
