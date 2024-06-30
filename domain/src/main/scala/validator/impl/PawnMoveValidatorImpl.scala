package validator.impl

import exception.GameException
import model.Position
import validator.PawnMoveValidator
import zio.ZLayer

case class PawnMoveValidatorImpl() extends PawnMoveValidator {
  override def validate(from: Position, to: Position, existing: Position): Either[GameException, Unit] = {
    if from.x == to.x then {
      if existing.x == to.x && (from.y < existing.y || existing.y > to.y) then Left(GameException.ObstacleDuringMove)
      else Right(())
    } else if from.y == to.y then {
      if existing.y == to.y && (from.x < existing.x || existing.x > to.x) then Left(GameException.ObstacleDuringMove)
      else Right(())
    } else {
      Left(GameException.UnsupportedPawnMoveDirection)
    }
  }
}

object PawnMoveValidatorImpl {
  lazy val live = ZLayer.derive[PawnMoveValidatorImpl]
}
