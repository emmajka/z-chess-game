package mysql.query

import io.getquill.*
import model.{ChessBoardType, ChessGameDetails}
import mysql.schema.*
import zio.*

case class ChessGameQueriesMirror(context: SqlMirrorContext[MirrorSqlDialect, SnakeCase])
    extends ChessGameQueries[MirrorSqlDialect] {
  import context.*

  def getChessGameDetailsByGameId(gameId: String): Task[QueryMirror[ChessGameDetails]] =
    ZIO.attempt(run(getChessGameDetailsByGameIdQuery(gameId = gameId)))

  def initGameOfChess(newGameId: String, chessBoardType: ChessBoardType) =
    ZIO.attempt(run(newChessGameInsert(newGameId = newGameId, chessBoardType = chessBoardType)))

}
