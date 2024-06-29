package validator.impl

import model.PieceCoordinates
import validator.PawnMoveDirectionValidator

case class PawnMoveDirectionValidatorImpl() extends PawnMoveDirectionValidator {
  override def validate(from: PieceCoordinates, to: PieceCoordinates): Boolean = from.x == to.x || from.y == to.y
}
