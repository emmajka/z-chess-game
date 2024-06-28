package mysql.schema

import io.getquill.*

import java.time.LocalDateTime

case class ChessGameStateTable(
  id: Long,
  gameId: String,
  pieceId: Long,
  xCoordinate: Int,
  yCoordinate: Int,
  active: Boolean
)

object ChessGameStateTable {
  import mysql.MysqlCtx.*

  val schema = schemaMeta[ChessGameStateTable](
    "chess_game_sate",
    _.id          -> "id",
    _.gameId      -> "game_id",
    _.pieceId     -> "piece_id",
    _.xCoordinate -> "x_coordinate",
    _.yCoordinate -> "y_coordinate",
    _.active      -> "active"
  )
}
