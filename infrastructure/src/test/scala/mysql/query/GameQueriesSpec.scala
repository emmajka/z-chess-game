package mysql.query

import io.getquill.*
import model.{PieceType, Position}
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
        sql == "SELECT gpt.piece_id AS pieceId, gpt.piece_type AS pieceType, gpt.x_coordinate AS xCoordinate, gpt.y_coordinate AS yCoordinate, gpt.active FROM game gt INNER JOIN game_pieces gpt ON gpt.game_id = gt.game_id WHERE gt.game_id = ?"
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
        sql == "UPDATE game_pieces AS gpt SET active = false WHERE gpt.game_id = ? AND gpt.piece_id = ?"
      )
    }
    test("gamePiecePositionUpdate SQL content test") {
      for
        mirror <- mirrorRepo.updatePiecePosition(gameId = "gameId", pieceId = 1, x = 1, y = 2)
        sql = mirror.string
      yield assertTrue(sql == "UPDATE game_pieces AS gpt SET x_coordinate = ?, y_coordinate = ? WHERE gpt.game_id = ? AND gpt.piece_id = ?")
    }
  }
}
