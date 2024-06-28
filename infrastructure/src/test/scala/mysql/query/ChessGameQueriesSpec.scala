package mysql.query

import io.getquill.*
import model.ChessBoardType
import zio.*
import zio.test.*

object ChessGameQueriesSpec extends ZIOSpecDefault {

  val spec = suiteAll("ChessGameQueries tests ") {
    val mirrorRepo = ChessGameQueriesMirror(context = SqlMirrorContext(MirrorSqlDialect, SnakeCase))
    test("getChessGameDetailsByGameIdQuery SQL content test") {
      for mirror <- mirrorRepo.getChessGameDetailsByGameId("test123")
      sql = mirror.string
      yield assertTrue(sql == "SELECT cgt.id, cgt.game_id AS gameId FROM chess_game cgt WHERE cgt.game_id = ?")
    }
    test("newChessGameInsert SQL content test") {
      for mirror <- mirrorRepo.initGameOfChess("someId", ChessBoardType.Standard)
      sql = mirror.string
      yield assertTrue(sql == "INSERT INTO chess_game (game_id,board_height,board_width) VALUES (?, ?, ?)")
    }
  }
}
