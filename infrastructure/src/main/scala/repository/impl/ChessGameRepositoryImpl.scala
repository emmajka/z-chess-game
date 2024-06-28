package repository.impl

import io.getquill.*

import model.{ChessGameDetails, ChessGamePiecesDetails}
import mysql.MysqlCtx
import mysql.query.ChessGameQueries
import repository.{ChessGameRepository, MysqlRepository}
import zio._

import java.sql.SQLException
import javax.sql.DataSource

case class ChessGameRepositoryImpl(context: MysqlCtx, datasource: DataSource)
    extends ChessGameRepository
      with ChessGameQueries[MySQLDialect]
      with MysqlRepository {

  import context.*
  import mysql.serde.ChessPieceType._
  override def getChessGameDetails(gameId: String): IO[Exception, Seq[ChessGameDetails]] =
    executeSelect(getChessGameDetailsByGameIdQuery(gameId = gameId))

  override def initGameOfChess(newGameId: String): IO[Exception, Long] =
    executeInsert(chessGameInsert(newGameId = newGameId))

  override def getChessGamePiecesDetails(gameId: String): IO[Exception, Seq[ChessGamePiecesDetails]] =
    executeSelect(getChessGamePiecesDetailsQuery(gameId = gameId))
}

object ChessGameRepositoryImpl {
  lazy val live = ZLayer.derive[ChessGameRepositoryImpl]
}
