package mysql.query

import io.getquill.*
import zio.*
import zio.test.*

object ChessGameQueriesSpec extends ZIOSpecDefault {

  val spec = suiteAll("ChessGameQueries tests ") {
    val mirrorRepo = ChessGameQueriesMirror(context = SqlMirrorContext(MirrorSqlDialect, SnakeCase))
    test("getChessGameDetailsByGameIdQuery SQL content test") {
      for
        mirror <- mirrorRepo.getChessGameDetailsByGameId("test123")
        sql = mirror.string
      yield assertTrue(sql == "SELECT cgt.id, cgt.game_id AS gameId FROM chess_game cgt WHERE cgt.game_id = ?")
    }
    test("chessGameInsert SQL content test") {
      for
        mirror <- mirrorRepo.createGameOfChess("someId")
        sql = mirror.string
      yield assertTrue(sql == "INSERT INTO chess_game (game_id) VALUES (?)")
    }
    test("getChessGamePiecesDetailsQuery SQL content test") {
      for
        mirror <- mirrorRepo.getChessGamePiecesDetails("someIdddd")
        sql = mirror.string
      yield assertTrue(sql == "SELECT cgp.piece_id AS pieceId, cgp.piece_type AS pieceType, cgp.x_coordinate AS xCoordinate, cgp.y_coordinate AS yCoordinate FROM chess_game cgt INNER JOIN chess_game_pieces cgp ON cgp.game_id = cgt.game_id WHERE cgt.game_id = ? AND cgp.active")
    }
  }
}
