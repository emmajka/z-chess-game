package service.impl

import exception.GameException
import exception.GameException.PiecePlaceTaken
import model.{GamePiecesDetails, PieceCoordinates}
import service.{PieceIdCreator, PieceIdGenerator}
import validator.GamePieceValidator
import zio.ZLayer

case class PieceIdCreatorImpl(gamePieceValidator: GamePieceValidator, pieceIdGenerator: PieceIdGenerator) extends PieceIdCreator {
  override def create(gameId: String, newPieceCoordinate: PieceCoordinates, gamePiecesDetails: Seq[GamePiecesDetails]): Either[GameException, Int] = {
    gamePiecesDetails.find(existingPiece => gamePieceValidator.isGamePieceTaken(existingPiece = existingPiece, newPieceCoordinates = newPieceCoordinate)) match
      case Some(takenPiece) => Left(PiecePlaceTaken(gameId = gameId, pieceId = takenPiece.pieceId, pieceCoordinates = newPieceCoordinate))
      case None => Right(pieceIdGenerator.generate(gamePiecesDetails))
  }
}

object PieceIdCreatorImpl {
  lazy val live = ZLayer.derive[PieceIdCreatorImpl]
}