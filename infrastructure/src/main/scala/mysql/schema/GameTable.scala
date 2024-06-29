package mysql.schema

import io.getquill.*

case class GameTable(
  id: Long,
  gameId: String
)

object GameTable {
  import mysql.MysqlCtx.*

  val schema = schemaMeta[GameTable](
    "game",
    _.id          -> "id",
    _.gameId      -> "game_id"
  )
}
