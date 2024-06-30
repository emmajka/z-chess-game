package validator.impl

import model.Position
import validator.PawnMoveDirectionValidator

case class PawnMoveDirectionValidatorImpl() extends PawnMoveDirectionValidator {
  override def validate(from: Position, to: Position): Boolean = from.x == to.x || from.y == to.y
}
