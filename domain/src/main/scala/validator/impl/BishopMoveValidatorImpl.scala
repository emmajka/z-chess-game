package validator.impl

import exception.GameException
import exception.GameException.{ObstacleDuringMove, UnsupportedBishopMoveDirection}
import model.Position
import validator.BishopMoveValidator
import zio.ZLayer

import java.lang.Math.abs

case class BishopMoveValidatorImpl() extends BishopMoveValidator {
  override def validate(from: Position, to: Position, existing: Position): Either[GameException, Unit] = {
    if abs(from.x - to.x) == abs(from.y - to.y) then {
      val xStepIncludingDirection = if to.x > from.x then 1 else -1
      val yStepIncludingDirection = if to.y > from.y then 1 else -1

      var piecePositionPostMoveX = from.x
      var piecePositionPostMoveY = from.y
      while piecePositionPostMoveX != to.x && piecePositionPostMoveY != to.y do {
        if existing.x == piecePositionPostMoveX && existing.y == piecePositionPostMoveY then {
          return Left(ObstacleDuringMove)
        }
        piecePositionPostMoveX += xStepIncludingDirection
        piecePositionPostMoveY += yStepIncludingDirection
      }
      Right(())
    } else {
      Left(UnsupportedBishopMoveDirection)
    }
  }
}

object BishopMoveValidatorImpl {
  lazy val live = ZLayer.derive[BishopMoveValidatorImpl]
}
