package mysql.query

import io.getquill.*
import model.{ChessGameDetails, ChessGamePiecesDetails}
import mysql.schema.*
import zio.*
import mysql.serde.ChessPieceType._

case class ChessGameQueriesMirror(context: SqlMirrorContext[MirrorSqlDialect, SnakeCase]) extends ChessGameQueries[MirrorSqlDialect] {
  import context.*
  def getChessGameDetailsByGameId(gameId: String): Task[QueryMirror[ChessGameDetails]] =
    ZIO.attempt(run(getChessGameDetailsByGameIdQuery(gameId = gameId)))

  def createGameOfChess(newGameId: String) =
    ZIO.attempt(run(chessGameInsert(newGameId = newGameId)))

  import mysql.serde.ChessPieceType._
  def getChessGamePiecesDetails(gameId: String): Task[QueryMirror[ChessGamePiecesDetails]] =
    ZIO.attempt(run(getChessGamePiecesDetailsQuery(gameId = gameId)))

}
