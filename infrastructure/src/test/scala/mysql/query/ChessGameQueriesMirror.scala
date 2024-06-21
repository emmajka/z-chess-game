package mysql.query

import io.getquill.*
import model.ChessGameDetails
import mysql.schema._
import zio.*

case class ChessGameQueriesMirror(context: SqlMirrorContext[MirrorSqlDialect, SnakeCase])
    extends ChessGameQueries[MirrorSqlDialect] {
  import context.*

  def getChessGameDetailsByGameId(gameId: String): Task[QueryMirror[ChessGameDetails]] =
    ZIO.attempt(run(getChessGameDetailsByGameIdQuery(gameId = gameId)))

  def initGameOfChess(newGameId: String) =
    ZIO.attempt(run(initGameOfChessQuery(newGameId = newGameId)))

}
