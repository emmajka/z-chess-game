package mysql.query

import io.getquill.*
import io.getquill.context.*
import io.getquill.idiom.*
import model.ChessGamePiecesDetails
import mysql.schema.*

trait ChessGamePiecesQueries [I <: Idiom]{
  val context: Context[I, SnakeCase]
  import context.*

  inline given SchemaMeta[ChessGameTable] = ChessGameTable.schema
  inline given SchemaMeta[ChessGamePiecesTable] = ChessGamePiecesTable.schema
  
  inline def getChessGamePiecesDetailsByGameIdQuery(gameId: String) =
    for chessGame <- query[ChessGameTable]
      .filter(cgt => cgt.gameId == lift(gameId))
    chessGamePieces <- query[ChessGamePiecesTable]
      .join(cgp => cgp.gameId == chessGame.gameId)
      .filter(cgp => cgp.active)  
      yield ChessGamePiecesDetails(pieceId = chessGamePieces.pieceId, pieceType = chessGamePieces.pieceType, xCoordinate = chessGamePieces.xCoordinate, yCoordinate = chessGamePieces.yCoordinate)

}
