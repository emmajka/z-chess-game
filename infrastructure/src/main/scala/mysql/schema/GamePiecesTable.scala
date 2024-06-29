package mysql.schema

import io.getquill.*
import model.PieceType

case class GamePiecesTable(
  id: Long,
  gameId: String,
  pieceId: Int,
  pieceType: PieceType,
  xCoordinate: Int,
  yCoordinate: Int,
  active: Boolean)

object GamePiecesTable {
  import mysql.MysqlCtx.*
  import mysql.serde.PieceType.*
  val schema = schemaMeta[GamePiecesTable](
    "game_pieces",
    _.id -> "id",
    _.gameId -> "game_id",
    _.pieceId -> "piece_id",
    _.pieceType -> "piece_type",
    _.xCoordinate -> "x_coordinate",
    _.yCoordinate -> "y_coordinate",
    _.active -> "active"
  )
}
