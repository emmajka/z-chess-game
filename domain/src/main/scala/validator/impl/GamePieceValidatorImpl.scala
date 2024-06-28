package validator.impl

import model.{ChessGamePiecesDetails, PieceCoordinates}
import validator.GamePieceValidator
import zio.ZLayer

case class GamePieceValidatorImpl() extends GamePieceValidator {
  override def isGamePieceTaken(existingPiece: ChessGamePiecesDetails, newPieceCoordinates: PieceCoordinates): Boolean =
    existingPiece.xCoordinate == newPieceCoordinates.x && existingPiece.yCoordinate == newPieceCoordinates.y && existingPiece.active
}

object GamePieceValidatorImpl {
  lazy val live = ZLayer.derive[GamePieceValidatorImpl]
}
