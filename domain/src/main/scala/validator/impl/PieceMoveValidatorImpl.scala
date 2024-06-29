package validator.impl

import exception.GameException
import exception.GameException.{ObstacleDuringMove, PawnNotStraightMove}
import model.{PieceCoordinates, PieceType}
import validator.{PawnMoveDirectionValidator, PawnObstacleDetector, PieceMoveValidator}

case class PieceMoveValidatorImpl(pawnMoveDirectionValidator: PawnMoveDirectionValidator, pawnObstacleDetector: PawnObstacleDetector)
    extends PieceMoveValidator {
  override def validate(pieceType: PieceType, from: PieceCoordinates, to: PieceCoordinates, existing: PieceCoordinates): Either[GameException, Unit] = {
    pieceType match
      case PieceType.Pawn => {
        for
          _ <- if pawnMoveDirectionValidator.validate(from = from, to = to) then Right(()) else Left(PawnNotStraightMove)
          _ <- if !pawnObstacleDetector.detect(from = from, to = to, existing = existing) then Right(()) else Left(ObstacleDuringMove)
        yield ()
      }
      case PieceType.Bishop => Right(())
  }
}
