package validator

import model.PieceCoordinates

trait PawnMoveDirectionValidator {
  def validate(from: PieceCoordinates, to: PieceCoordinates): Boolean
}
