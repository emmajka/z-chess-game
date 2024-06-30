package validator

import exception.GameException
import model.Position

trait PawnMoveValidator {
  def validate(from: Position, to: Position, existing: Position): Either[GameException, Unit]
}
