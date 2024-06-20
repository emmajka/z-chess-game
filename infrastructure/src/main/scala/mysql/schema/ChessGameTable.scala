package mysql.schema

import io.getquill.*

case class ChessGameTable(id: Long, gameId: String)

object ChessGameTable {
  import mysql.MysqlCtx.*

  val schema = schemaMeta[ChessGameTable](
    "chess_game",
    _.id     -> "id",
    _.gameId -> "game_id"
  )
}
