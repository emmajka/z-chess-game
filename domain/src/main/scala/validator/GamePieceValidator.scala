package validator

import model.{GamePiecesDetails, PieceCoordinates}

trait GamePieceValidator {
  def isGamePieceTaken(existingPiece: GamePiecesDetails, newPieceCoordinates: PieceCoordinates): Boolean
}
