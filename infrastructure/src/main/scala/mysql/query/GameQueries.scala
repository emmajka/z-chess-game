package mysql.query

import io.getquill.*
import io.getquill.context.*
import io.getquill.idiom.*
import model.{GameDetails, GamePiecesDetails, PieceType, Position}
import mysql.schema.*

trait GameQueries[I <: Idiom] {
  val context: Context[I, SnakeCase]
  import context.*

  inline given SchemaMeta[GameTable] = GameTable.schema
  inline given SchemaMeta[GamePiecesTable] = GamePiecesTable.schema

  inline def getGameDetailsQuery(gameId: String) =
    for game <- query[GameTable]
        .filter(cgt => cgt.gameId == lift(gameId))
    yield GameDetails(id = game.id, gameId = game.gameId)

  inline def gameInsert(newGameId: String) =
    query[GameTable]
      .insert(_.gameId -> lift(newGameId))
      .returning(_.gameId)

  inline def gamePieceInsert(gameId: String, pieceId: Int, pieceType: PieceType, xCoordinate: Int, yCoordinate: Int) =
    query[GamePiecesTable]
      .insert(
        _.gameId -> lift(gameId),
        _.pieceId -> lift(pieceId),
        _.pieceType -> lift(pieceType),
        _.xCoordinate -> lift(xCoordinate),
        _.yCoordinate -> lift(yCoordinate),
        _.active -> true
      )
  inline def gamePieceDeactivationUpdate(gameId: String, pieceId: Int) =
    query[GamePiecesTable]
      .filter(gpt => gpt.gameId == lift(gameId) && gpt.pieceId == lift(pieceId))
      .update(_.active -> false)
  
  inline def gamePiecePositionUpdate(gameId: String, pieceId: Int, x: Int, y:Int) =
    query[GamePiecesTable]
      .filter(gpt => gpt.gameId == lift(gameId) && gpt.pieceId == lift(pieceId))
      .update(_.xCoordinate -> lift(x), _.yCoordinate -> lift(y))

  inline def getGamePiecesDetailsQuery(gameId: String) =
    for
      game <- query[GameTable]
        .filter(gt => gt.gameId == lift(gameId))
      gamePiece <- query[GamePiecesTable]
        .join(gpt => gpt.gameId == game.gameId)
    yield GamePiecesDetails(
      pieceId = gamePiece.pieceId,
      pieceType = gamePiece.pieceType,
      xCoordinate = gamePiece.xCoordinate,
      yCoordinate = gamePiece.yCoordinate,
      active = gamePiece.active
    )
//  inline def insertIntoChessGamePiecesTable(gameId: String, pieceType:ChessPieceType): Insert[ChessGamePiecesTable] = {
//    query[ChessGamePiecesTable].insert(
//      _.gameId -> lift(gameId),
//      _.pieceType -> lift(pieceType),
//    )
//  }

}
