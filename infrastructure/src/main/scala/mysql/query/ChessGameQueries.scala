package mysql.query

import io.getquill.*
import io.getquill.context.*
import io.getquill.idiom.*
import model.ChessGameDetails
import mysql.schema._

trait ChessGameQueries [I <: Idiom]{
  val context: Context[I, SnakeCase]
  import context._
  
  inline given SchemaMeta[ChessGameTable] = ChessGameTable.schema
  
  inline def getChessGameDetailsByGameId(gameId: String) =
    for chessGame <- query[ChessGameTable].filter(_.gameId == gameId) yield ChessGameDetails(id = chessGame.id, gameId = chessGame.gameId)

}
