package validator

import model.{ChessGamePiecesDetails, PieceCoordinates}

trait GamePieceValidator {
  def isGamePieceTaken(existingPiece: ChessGamePiecesDetails, newPieceCoordinates: PieceCoordinates): Boolean
}
