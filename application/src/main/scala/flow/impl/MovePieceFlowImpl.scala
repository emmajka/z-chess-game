package flow.impl

import exception.GameException.{GameNotExists, PieceNotExists}
import flow.MovePieceFlow
import model.Position
import repository.GameRepository
import validator.PieceMoveValidator
import zio.{Task, ZIO, ZLayer}

case class MovePieceFlowImpl(gameRepository: GameRepository, pieceMoveValidator: PieceMoveValidator) extends MovePieceFlow {
  override def run(gameId: String, pieceId: Int, targetPosition: Position): Task[Unit] =
    for
      gameDetails <- gameRepository.getGameDetails(gameId).filterOrFail(_.nonEmpty)(GameNotExists(gameId = gameId))
      allActivePieces <- gameRepository.getGamePiecesDetails(gameId = gameId).map(_.filter(_.active))
      currentPieceDetails <- ZIO
        .fromOption(allActivePieces.find(p => p.pieceId == pieceId))
        .orElseFail(PieceNotExists(gameId = gameId, pieceId = pieceId))
      _ <- ZIO.foreachDiscard(allActivePieces.filterNot(_.pieceId == pieceId)) { // TODO: use zio Validation from zio-prelude lib
        existing =>
          ZIO.fromEither {
            pieceMoveValidator.validate(
              currentPieceDetails.pieceType,
              Position(currentPieceDetails.xCoordinate, currentPieceDetails.yCoordinate),
              targetPosition,
              Position(existing.xCoordinate, existing.yCoordinate)
            )
          }
      }
      _ <- gameRepository.updatePiecePosition(gameId = gameId, pieceId = pieceId, targetPosition)
    yield ()

}

object MovePieceFlowImpl {
  lazy val live = ZLayer.derive[MovePieceFlowImpl]
}
