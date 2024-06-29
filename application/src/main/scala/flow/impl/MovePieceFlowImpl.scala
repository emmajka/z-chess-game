package flow.impl

import exception.GameException.{GameNotExists, PieceNotExists}
import flow.MovePieceFlow
import model.{PieceCoordinates, PieceType}
import repository.GameRepository
import validator.PieceMoveValidator
import zio.{Task, ZIO, ZLayer}

case class MovePieceFlowImpl(gameRepository: GameRepository, pieceMoveValidator: PieceMoveValidator) extends MovePieceFlow {
  override def run(gameId: String, pieceId: Int, pieceType: PieceType, pieceMoveToCoordinates: PieceCoordinates): Task[Unit] =
    for
      gameDetails <- gameRepository.getGameDetails(gameId)
      _ <- ZIO.fail(GameNotExists(gameId = gameId)).when(gameDetails.isEmpty)
      allActivePieces <- gameRepository.getGamePiecesDetails(gameId = gameId).map(_.filter(_.active))
      currentPieceDetails <- ZIO
        .fromOption(allActivePieces.find(p => p.pieceId == pieceId))
        .orElseFail(PieceNotExists(gameId = gameId, pieceId = pieceId))
      _ <- ZIO.foreach(allActivePieces)(
        existing =>
          ZIO.fromEither {
            pieceMoveValidator.validate(
              currentPieceDetails.pieceType,
              pieceMoveToCoordinates /* FIXME incorrect coordinates, extract from DB!*/,
              pieceMoveToCoordinates,
              PieceCoordinates(existing.xCoordinate, existing.yCoordinate)
            )
          }
      )
    yield ()

}

object MovePieceFlowImpl {
  lazy val live = ZLayer.derive[MovePieceFlowImpl]
}
