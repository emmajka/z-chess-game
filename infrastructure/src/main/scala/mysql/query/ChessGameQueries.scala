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
  
  inline def getChessGameDetailsByGameId(gameId: String) =
    for chessGame <- query[ChessGameTable].filter(_.gameId == lift(gameId)) yield ChessGameDetails(id = chessGame.id, gameId = chessGame.gameId)

}
