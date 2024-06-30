package validator.impl

import exception.GameException
import model.{PieceType, Position}
import validator.{PawnMoveValidator, PieceMoveValidator}
import zio.ZLayer

case class PieceMoveValidatorImpl(pawnMoveValidator: PawnMoveValidator) extends PieceMoveValidator {
  override def validate(pieceType: PieceType, from: Position, to: Position, existing: Position): Either[GameException, Unit] = {
    pieceType match
      case PieceType.Pawn => pawnMoveValidator.validate(from = from, to = to, existing = existing)
      case PieceType.Bishop => Right(())
  }
}

object PieceMoveValidatorImpl {
  lazy val live = ZLayer.derive[PieceMoveValidatorImpl]
}
