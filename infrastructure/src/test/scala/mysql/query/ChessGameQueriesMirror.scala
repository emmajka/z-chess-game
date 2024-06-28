package mysql.query

import io.getquill.*
import model.{ChessGameDetails, ChessGamePiecesDetails, ChessPieceType}
import mysql.schema.*
import mysql.serde.ChessPieceType.*
import zio.*

case class ChessGameQueriesMirror(context: SqlMirrorContext[MirrorSqlDialect, SnakeCase]) extends ChessGameQueries[MirrorSqlDialect] {
  import context.*
  def getChessGameDetailsByGameId(gameId: String): Task[QueryMirror[ChessGameDetails]] =
    ZIO.attempt(run(getChessGameDetailsByGameIdQuery(gameId = gameId)))

  def createGameOfChess(newGameId: String) =
    ZIO.attempt(run(chessGameInsert(newGameId = newGameId)))

  import mysql.serde.ChessPieceType.*
  def getChessGamePiecesDetails(gameId: String): Task[QueryMirror[ChessGamePiecesDetails]] =
    ZIO.attempt(run(getChessGamePiecesDetailsQuery(gameId = gameId)))

  def createChessGamePiece(gameId: String, pieceId: Int, pieceType: ChessPieceType, xCoordinate: Int, yCoordinate: Int) =
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

}
