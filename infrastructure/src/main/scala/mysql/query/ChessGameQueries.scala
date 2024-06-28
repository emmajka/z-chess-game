package mysql.query

import io.getquill.*
import io.getquill.context.*
import io.getquill.idiom.*
import model.{ChessGameDetails, ChessGamePiecesDetails, ChessPieceType}
import mysql.schema.*

trait ChessGameQueries[I <: Idiom] {
  val context: Context[I, SnakeCase]
  import context.*

  inline given SchemaMeta[ChessGameTable] = ChessGameTable.schema
  inline given SchemaMeta[ChessGamePiecesTable] = ChessGamePiecesTable.schema

  inline def getChessGameDetailsQuery(gameId: String) =
    for chessGame <- query[ChessGameTable]
        .filter(cgt => cgt.gameId == lift(gameId))
    yield ChessGameDetails(id = chessGame.id, gameId = chessGame.gameId)

  inline def chessGameInsert(newGameId: String) =
    query[ChessGameTable]
      .insert(_.gameId -> lift(newGameId))

  inline def chessGamePieceInsert(gameId: String, pieceId: Int, pieceType: ChessPieceType, xCoordinate: Int, yCoordinate: Int) =
    query[ChessGamePiecesTable]
      .insert(
        _.gameId -> lift(gameId),
        _.pieceId -> lift(pieceId),
        _.pieceType -> lift(pieceType),
        _.xCoordinate -> lift(xCoordinate),
        _.yCoordinate -> lift(yCoordinate),
        _.active -> true
      )
  inline def chessGamePieceDeactivationUpdate(gameId: String, pieceId: Int) =
    query[ChessGamePiecesTable]
      .filter(cgpt => cgpt.gameId == lift(gameId) && cgpt.pieceId == lift(pieceId))
      .update(_.active -> false)

  inline def getChessGamePiecesDetailsQuery(gameId: String) =
    for
      chessGame <- query[ChessGameTable]
        .filter(cgt => cgt.gameId == lift(gameId))
      chessGamePiece <- query[ChessGamePiecesTable]
        .join(cgp => cgp.gameId == chessGame.gameId)
    yield ChessGamePiecesDetails(
      pieceId = chessGamePiece.pieceId,
      pieceType = chessGamePiece.pieceType,
      xCoordinate = chessGamePiece.xCoordinate,
      yCoordinate = chessGamePiece.yCoordinate,
      active = chessGamePiece.active
    )
//  inline def insertIntoChessGamePiecesTable(gameId: String, pieceType:ChessPieceType): Insert[ChessGamePiecesTable] = {
//    query[ChessGamePiecesTable].insert(
//      _.gameId -> lift(gameId),
//      _.pieceType -> lift(pieceType),
//    )
//  }

}
