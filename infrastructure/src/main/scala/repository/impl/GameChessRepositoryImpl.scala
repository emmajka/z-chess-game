package repository.impl

import io.getquill.*
import io.getquill.context.*
import io.getquill.context.qzio.ImplicitSyntax.*
import model.ChessGameDetails
import mysql.MysqlCtx
import mysql.query.ChessGameQueries
import mysql.schema.ChessGameTable
import repository.{ChessGameRepository, MysqlRepository}
import zio.{IO, ZLayer}

import java.sql.SQLException
import javax.sql.DataSource

case class GameChessRepositoryImpl(context: MysqlCtx, datasource: DataSource)
    extends ChessGameRepository
    with ChessGameQueries[MySQLDialect]
    with MysqlRepository {

  import context.*
  override def getChessGameDetails(gameId: String): IO[Exception, Seq[ChessGameDetails]] =
    executeSelect(getChessGameDetailsByGameIdQuery(gameId = gameId))
  
  override def initGameOfChess(newGameId: String): IO[Exception, Long] = {
    executeInsert(newChessGameInsert(newGameId = newGameId))
  }
}

object GameChessRepositoryImpl {
  lazy val live = ZLayer.derive[GameChessRepositoryImpl]
}
