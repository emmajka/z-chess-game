package repository.impl

import exception.ChessGameException.ChessGameNotExists
import io.getquill.*
import model.{ChessGameDetails, ChessGamePiecesDetails, ChessPieceType, PieceCoordinates}
import mysql.MysqlCtx
import mysql.query.ChessGameQueries
import repository.{ChessGameRepository, MysqlRepository}
import zio.*

import java.sql.SQLException
import javax.sql.DataSource

case class ChessGameRepositoryImpl(context: MysqlCtx, datasource: DataSource)
    extends ChessGameRepository
      with ChessGameQueries[MySQLDialect]
      with MysqlRepository {

  import context.*
  import mysql.serde.ChessPieceType.*
  override def getChessGameDetails(gameId: String): IO[Exception, ChessGameDetails] = {
    for
      qr <- executeSelect(getChessGameDetailsByGameIdQuery(gameId = gameId))
      result <- if qr.isEmpty then ZIO.fail(ChessGameNotExists(gameId = gameId)) else ZIO.succeed(qr.head)
    yield result
  }

  override def initGameOfChess(newGameId: String): IO[Exception, Long] =
    executeInsert(chessGameInsert(newGameId = newGameId))

  override def getChessGamePiecesDetails(gameId: String): IO[Exception, Seq[ChessGamePiecesDetails]] =
    executeSelect(getChessGamePiecesDetailsQuery(gameId = gameId))

  override def createNewChessGamePiece(gameId: String, pieceId: Int, pieceType: ChessPieceType, coordinates: PieceCoordinates): IO[Exception, Long] =
    executeInsert(
      chessGamePieceInsert(
        gameId = gameId,
        pieceId = pieceId,
        pieceType = pieceType,
        xCoordinate = coordinates.x,
        yCoordinate = coordinates.y
      )
    )
}

object ChessGameRepositoryImpl {
  lazy val live = ZLayer.derive[ChessGameRepositoryImpl]
}
