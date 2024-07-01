package http.route.impl

import flow.*
import http.request.{AddPieceRequest, MovePieceRequest}
import http.response.{ApplicationHttpResponse, ErrorResponse, GetGameDetailsResponse}
import http.route.impl.GameRouteImpl.*
import http.route.{zServerCatchAll, GameRoute}
import sttp.tapir.endpoint
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import zio.*

case class GameRouteImpl(
  addPieceFlow: AddPieceFlow,
  createNewGameFlow: CreateNewGameFlow,
  getGameDetailsFlow: GetGameDetailsFlow,
  deletePieceFlow: DeletePieceFlow,
  movePieceFlow: MovePieceFlow)
    extends GameRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = Seq(
    initGameEndpoint.zServerLogic(
      _ => {
        for result <- createNewGameFlow.run()
        yield ApplicationHttpResponse(body = result)
      }
    ),
    getGameDetailsEndpoint.zServerLogic {
      gameId =>
        {
          for
            result <- getGameDetailsFlow.run(gameId)
            piecesDesc = result
              .pieces
              .filter(_.active)
              .map(p => s"${p.pieceType.code}${p.pieceId}: ${p.xCoordinate}:${p.yCoordinate}")
          yield ApplicationHttpResponse(body = GetGameDetailsResponse(gameId = result.gameId, pieces = piecesDesc))
        }
    },
    deletePieceEndpoint.zServerLogic {
      case (gameId: String, pieceId: Int) =>
        for _ <- deletePieceFlow.run(gameId = gameId, pieceId = pieceId)
        yield ApplicationHttpResponse(body = s"Successfully deletes piece with ID [$pieceId] from game with ID [$gameId]")
    },
    addPieceEndpoint.zServerLogic {
      case (gameId: String, req: AddPieceRequest) =>
        for result <- addPieceFlow.run(gameId = gameId, pieceType = req.pieceType, targetPosition = req.targetPosition)
        yield ApplicationHttpResponse(body = s"Successfully added new piece to game with ID [$gameId], new piece's ID is $result")
    },
    movePieceEndpoint.zServerLogic {
      case (gameId: String, pieceId: Int, req: MovePieceRequest) =>
        for _ <- movePieceFlow.run(gameId = gameId, pieceId = pieceId, targetPosition = req.targetPosition)
        yield ApplicationHttpResponse(body = s"Successfully moved a piece]")
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
