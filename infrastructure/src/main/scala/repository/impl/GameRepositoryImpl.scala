package repository.impl

import io.getquill.*
import model.{GameDetails, GamePiecesDetails, PieceType, PieceCoordinates}
import mysql.MysqlCtx
import mysql.query.GameQueries
import repository.{GameRepository, MysqlRepository}
import zio.*

import java.sql.SQLException
import javax.sql.DataSource

case class GameRepositoryImpl(context: MysqlCtx, datasource: DataSource)
    extends GameRepository
      with GameQueries[MySQLDialect]
      with MysqlRepository {

  import context.*
  import mysql.serde.PieceType.*
  override def getGameDetails(gameId: String): IO[Exception, Seq[GameDetails]] =
    executeSelect(getGameDetailsQuery(gameId = gameId))

  override def createNewGame(newGameId: String): IO[Exception, Long] =
    executeInsert(gameInsert(newGameId = newGameId))

  override def getGamePiecesDetails(gameId: String): IO[Exception, Seq[GamePiecesDetails]] =
    executeSelect(getGamePiecesDetailsQuery(gameId = gameId))

  override def addNewGamePiece(gameId: String, pieceId: Int, pieceType: PieceType, coordinates: PieceCoordinates): IO[Exception, Long] =
    executeInsert(
      gamePieceInsert(
        gameId = gameId,
        pieceId = pieceId,
        pieceType = pieceType,
        xCoordinate = coordinates.x,
        yCoordinate = coordinates.y
      )
    )

  override def deleteGamePiece(gameId: String, pieceId: Index): IO[Exception, Long] = executeUpdate(
    gamePieceDeactivationUpdate(gameId = gameId, pieceId = pieceId)
  )

}

object GameRepositoryImpl {
  lazy val live = ZLayer.derive[GameRepositoryImpl]
}
