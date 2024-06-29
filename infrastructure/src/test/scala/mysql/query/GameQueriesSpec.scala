package mysql.query

import io.getquill.*
import model.PieceType
import zio.*
import zio.test.*

object GameQueriesSpec extends ZIOSpecDefault {

  val spec = suiteAll("GameQueries tests ") {
    val mirrorRepo = GameQueriesMirror(context = SqlMirrorContext(MirrorSqlDialect, SnakeCase))
    test("getGameDetails SQL content test") {
      for
        mirror <- mirrorRepo.getGameDetails("test123")
        sql = mirror.string
      yield assertTrue(sql == "SELECT cgt.id, cgt.game_id AS gameId FROM game cgt WHERE cgt.game_id = ?")
    }
    test("gameInsert SQL content test") {
      for
        mirror <- mirrorRepo.createNewGame("someId")
        sql = mirror.string
      yield assertTrue(sql == "INSERT INTO game (game_id) VALUES (?)")
    }
    test("getGamePiecesDetailsQuery SQL content test") {
      for
        mirror <- mirrorRepo.getGamePiecesDetails("someIdddd")
        sql = mirror.string
      yield assertTrue(
        sql == "SELECT cgp.piece_id AS pieceId, cgp.piece_type AS pieceType, cgp.x_coordinate AS xCoordinate, cgp.y_coordinate AS yCoordinate, cgp.active FROM game cgt INNER JOIN game_pieces cgp ON cgp.game_id = cgt.game_id WHERE cgt.game_id = ?"
      )
    }
    test("gamePieceInsert SQL content test") {
      for
        mirror <- mirrorRepo.addGamePiece(gameId = "gameId", pieceId = 1, pieceType = PieceType.Pawn, xCoordinate = 1, yCoordinate = 2)
        sql = mirror.string
      yield assertTrue(
        sql == "INSERT INTO game_pieces (game_id,piece_id,piece_type,x_coordinate,y_coordinate,active) VALUES (?, ?, ?, ?, ?, true)"
      )
    }
    test("gamePieceDeactivationUpdate SQL content test") {
      for
        mirror <- mirrorRepo.deletePiece(gameId = "gameId", pieceId = 1)
        sql = mirror.string
      yield assertTrue(
        sql == "UPDATE game_pieces AS cgpt SET active = false WHERE cgpt.game_id = ? AND cgpt.piece_id = ?"
      )
    }
  }
}
