package mysql.query

import io.getquill.*
import io.getquill.context.*
import io.getquill.idiom.*
import model.ChessGameDetails
import mysql.schema.*

trait ChessGameQueries [I <: Idiom]{
  val context: Context[I, SnakeCase]
  import context.*
  
  inline given SchemaMeta[ChessGameTable] = ChessGameTable.schema
  
  inline def getChessGameDetailsByGameIdQuery(gameId: String) =
    for chessGame <- query[ChessGameTable].filter(cgt => cgt.gameId == lift(gameId)) 
      yield ChessGameDetails(id = chessGame.id, gameId = chessGame.gameId)
    
  inline def initGameOfChessQuery(newGameId: String): ActionReturning[ChessGameTable, ChessGameDetails] = 
    query[ChessGameTable]
    .insert(_.gameId -> lift(newGameId))
    .returningGenerated(created => ChessGameDetails(id = created.id, gameId = created.gameId))


}
