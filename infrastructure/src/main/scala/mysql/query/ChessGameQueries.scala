package mysql.query

import io.getquill.*
import io.getquill.context.*
import io.getquill.idiom.*
import model.{ChessBoardType, ChessGameDetails}
import mysql.schema.*

trait ChessGameQueries [I <: Idiom]{
  val context: Context[I, SnakeCase]
  import context.*
  
  inline given SchemaMeta[ChessGameTable] = ChessGameTable.schema
  
  inline def getChessGameDetailsByGameIdQuery(gameId: String) =
    for chessGame <- query[ChessGameTable].filter(cgt => cgt.gameId == lift(gameId)) 
      yield ChessGameDetails(id = chessGame.id, gameId = chessGame.gameId)
    
  inline def newChessGameInsert(newGameId: String, chessBoardType: ChessBoardType): Insert[ChessGameTable] =
    query[ChessGameTable].insert(
      _.gameId -> lift(newGameId),
      _.boardHeight -> lift(chessBoardType.height),
      _.boardWidth -> lift(chessBoardType.width)
    )

//  inline def insertIntoChessGamePiecesTable(gameId: String, pieceType:ChessPieceType): Insert[ChessGamePiecesTable] = {
//    query[ChessGamePiecesTable].insert(
//      _.gameId -> lift(gameId),
//      _.pieceType -> lift(pieceType),
//    )
//  }

}
