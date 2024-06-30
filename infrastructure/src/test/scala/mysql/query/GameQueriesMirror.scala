package mysql.query

import io.getquill.*
import model.{GameDetails, GamePiecesDetails, PieceType, Position}
import mysql.schema.*
import mysql.serde.PieceType.*
import zio.*

case class GameQueriesMirror(context: SqlMirrorContext[MirrorSqlDialect, SnakeCase]) extends GameQueries[MirrorSqlDialect] {
  import context.*
  def getGameDetails(gameId: String): Task[context.QueryMirror[GameDetails]] =
    ZIO.attempt(run(getGameDetailsQuery(gameId = gameId)))

  def createNewGame(newGameId: String): Task[context.ActionReturningMirror[String, String]] =
    ZIO.attempt(run(gameInsert(newGameId = newGameId)))

  import mysql.serde.PieceType.*
  def getGamePiecesDetails(gameId: String): Task[QueryMirror[GamePiecesDetails]] =
    ZIO.attempt(run(getGamePiecesDetailsQuery(gameId = gameId)))

  def addGamePiece(
    gameId: String,
    pieceId: Int,
    pieceType: PieceType,
    xCoordinate: Int,
    yCoordinate: Int
  ): Task[context.ActionMirror] =
    ZIO.attempt(
      run(
        gamePieceInsert(
          gameId = gameId,
          pieceId = pieceId,
          pieceType = pieceType,
          xCoordinate = xCoordinate,
          yCoordinate = yCoordinate
        )
      )
    )

  def deletePiece(gameId: String, pieceId: Int): Task[context.ActionMirror] =
    ZIO.attempt(run(gamePieceDeactivationUpdate(gameId = gameId, pieceId = pieceId)))

  def updatePiecePosition(gameId: String, pieceId: Int, x: Int, y: Int): Task[context.ActionMirror] =
    ZIO.attempt(run(gamePiecePositionUpdate(gameId = gameId, pieceId = pieceId, x = x, y = y)))

}
