package mysql.query

import io.getquill.*
import model.{ChessGameDetails, ChessGamePiecesDetails, ChessPieceType}
import mysql.schema.*
import mysql.serde.ChessPieceType.*
import zio.*

case class ChessGameQueriesMirror(context: SqlMirrorContext[MirrorSqlDialect, SnakeCase]) extends ChessGameQueries[MirrorSqlDialect] {
  import context.*
  def getChessGameDetails(gameId: String): Task[context.QueryMirror[ChessGameDetails]] =
    ZIO.attempt(run(getChessGameDetailsQuery(gameId = gameId)))

  def createGameOfChess(newGameId: String): Task[context.ActionMirror] =
    ZIO.attempt(run(chessGameInsert(newGameId = newGameId)))

  import mysql.serde.ChessPieceType.*
  def getChessGamePiecesDetails(gameId: String): Task[QueryMirror[ChessGamePiecesDetails]] =
    ZIO.attempt(run(getChessGamePiecesDetailsQuery(gameId = gameId)))

  def createChessGamePiece(
    gameId: String,
    pieceId: Int,
    pieceType: ChessPieceType,
    xCoordinate: Int,
    yCoordinate: Int
  ): Task[context.ActionMirror] =
    ZIO.attempt(
      run(
        chessGamePieceInsert(
          gameId = gameId,
          pieceId = pieceId,
          pieceType = pieceType,
          xCoordinate = xCoordinate,
          yCoordinate = yCoordinate
        )
      )
    )

  def deleteChessPiece(gameId: String, pieceId: Int): Task[context.ActionMirror] =
    ZIO.attempt(run(chessGamePieceDeactivationUpdate(gameId = gameId, pieceId = pieceId)))

}
