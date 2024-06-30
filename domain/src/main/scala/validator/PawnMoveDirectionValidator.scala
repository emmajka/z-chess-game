package validator

import model.Position

trait PawnMoveDirectionValidator {
  def validate(from: Position, to: Position): Boolean
}
