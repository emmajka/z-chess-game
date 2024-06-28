package mysql.schema

import io.getquill.*
import model.ChessPieceType

import java.time.LocalDateTime

case class ChessGamePiecesTable(
  id: Long,
  gameId: String,
  pieceId: Int,
  pieceType: ChessPieceType
)

object ChessGamePiecesTable {
  import mysql.MysqlCtx.*
  import mysql.serde.ChessPieceType.*
  val schema = schemaMeta[ChessGamePiecesTable](
    "chess_game_pieces",
    _.id        -> "id",
    _.gameId    -> "game_id",
    _.pieceId   -> "piece_id",
    _.pieceType -> "piece_type"
  )
}
