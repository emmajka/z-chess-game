package validator

import exception.GameException
import model.{Position, PieceType}

trait PieceMoveValidator {
  def validate(pieceType: PieceType, from: Position, to: Position, existing: Position): Either[GameException, Unit]
}
