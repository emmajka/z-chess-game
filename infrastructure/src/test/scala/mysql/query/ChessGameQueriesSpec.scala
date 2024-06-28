package mysql.query

import io.getquill.*
import model.ChessPieceType
import zio.*
import zio.test.*

object ChessGameQueriesSpec extends ZIOSpecDefault {

  val spec = suiteAll("ChessGameQueries tests ") {
    val mirrorRepo = ChessGameQueriesMirror(context = SqlMirrorContext(MirrorSqlDialect, SnakeCase))
    test("getChessGameDetails SQL content test") {
      for
        mirror <- mirrorRepo.getChessGameDetails("test123")
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
      yield assertTrue(
        sql == "SELECT cgp.piece_id AS pieceId, cgp.piece_type AS pieceType, cgp.x_coordinate AS xCoordinate, cgp.y_coordinate AS yCoordinate, cgp.active FROM chess_game cgt INNER JOIN chess_game_pieces cgp ON cgp.game_id = cgt.game_id WHERE cgt.game_id = ?"
      )
    }
    test("chessGamePieceInsert SQL content test") {
      for
        mirror <- mirrorRepo.createChessGamePiece(gameId = "gameId", pieceId = 1, pieceType = ChessPieceType.Pawn, xCoordinate = 1, yCoordinate = 2)
        sql = mirror.string
      yield assertTrue(
        sql == "INSERT INTO chess_game_pieces (game_id,piece_id,piece_type,x_coordinate,y_coordinate,active) VALUES (?, ?, ?, ?, ?, true)"
      )
    }
    test("chessGamePieceDeactivationUpdate SQL content test") {
      for
        mirror <- mirrorRepo.deleteChessPiece(gameId = "gameId", pieceId = 1)
        sql = mirror.string
      yield assertTrue(
        sql == "UPDATE chess_game_pieces AS cgpt SET active = false WHERE cgpt.game_id = ? AND cgpt.piece_id = ?"
      )
    }
  }
}
