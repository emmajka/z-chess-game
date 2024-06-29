package validator.impl

import model.{GamePiecesDetails, PieceCoordinates}
import validator.GamePieceValidator
import zio.ZLayer

case class GamePieceValidatorImpl() extends GamePieceValidator {
  override def isGamePieceTaken(existingPiece: GamePiecesDetails, newPieceCoordinates: PieceCoordinates): Boolean =
    existingPiece.xCoordinate == newPieceCoordinates.x && existingPiece.yCoordinate == newPieceCoordinates.y && existingPiece.active
}

object GamePieceValidatorImpl {
  lazy val live = ZLayer.derive[GamePieceValidatorImpl]
}
