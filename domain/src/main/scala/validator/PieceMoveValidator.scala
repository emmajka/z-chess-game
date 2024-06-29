package validator

import exception.GameException
import model.{PieceCoordinates, PieceType}

trait PieceMoveValidator {
  def validate(pieceType: PieceType, from: PieceCoordinates, to: PieceCoordinates, existing: PieceCoordinates): Either[GameException, Unit]
}
