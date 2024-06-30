package repository.impl

import io.getquill.*
import io.getquill.context.qzio.ImplicitSyntax.*
import model.{GameDetails, GamePiecesDetails, Position, PieceType}
import mysql.MysqlCtx
import mysql.query.GameQueries
import repository.{GameRepository, MysqlRepository}
import zio.*

import java.sql.SQLException
import javax.sql.DataSource
case class GameRepositoryImpl(context: MysqlCtx, datasource: DataSource) extends GameRepository with GameQueries[MySQLDialect] with MysqlRepository {

  import context.*
  import mysql.serde.PieceType.*

  override def getGameDetails(gameId: String): IO[Exception, Seq[GameDetails]] =
    run(getGameDetailsQuery(gameId = gameId)).implicitly

  override def createNewGame(newGameId: String): IO[Throwable, String] =
    transaction(run(gameInsert(newGameId = newGameId))).implicitly

  override def getGamePiecesDetails(gameId: String): IO[Exception, List[GamePiecesDetails]] =
    run(getGamePiecesDetailsQuery(gameId = gameId)).implicitly

  override def addNewGamePiece(gameId: String, pieceId: Int, pieceType: PieceType, coordinates: Position): IO[Throwable, Long] =
    transaction(
      run(
        gamePieceInsert(
          gameId = gameId,
          pieceId = pieceId,
          pieceType = pieceType,
          xCoordinate = coordinates.x,
          yCoordinate = coordinates.y
        )
      )
    ).implicitly

  override def deleteGamePiece(gameId: String, pieceId: Index): IO[Throwable, Long] =
    transaction(run(gamePieceDeactivationUpdate(gameId = gameId, pieceId = pieceId))).implicitly

}

object GameRepositoryImpl {
  lazy val live = ZLayer.derive[GameRepositoryImpl]
}
