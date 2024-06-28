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

  inline def getChessGameDetailsByGameIdQuery(gameId: String) =
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

  inline def getChessGamePiecesDetailsQuery(gameId: String) =
    for
      chessGame <- query[ChessGameTable]
        .filter(cgt => cgt.gameId == lift(gameId))
      chessGamePieces <- query[ChessGamePiecesTable]
        .join(cgp => cgp.gameId == chessGame.gameId)
        .filter(cgp => cgp.active)
    yield ChessGamePiecesDetails(
      pieceId = chessGamePieces.pieceId,
      pieceType = chessGamePieces.pieceType,
      xCoordinate = chessGamePieces.xCoordinate,
      yCoordinate = chessGamePieces.yCoordinate
    )
//  inline def insertIntoChessGamePiecesTable(gameId: String, pieceType:ChessPieceType): Insert[ChessGamePiecesTable] = {
//    query[ChessGamePiecesTable].insert(
//      _.gameId -> lift(gameId),
//      _.pieceType -> lift(pieceType),
//    )
//  }

}
