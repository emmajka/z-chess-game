package service.impl

import exception.ChessGameException
import exception.ChessGameException.ChessPiecePlaceTaken
import model.{ChessGamePiecesDetails, PieceCoordinates}
import service.{PieceIdCreator, PieceIdGenerator}
import validator.GamePieceValidator
import zio.ZLayer

case class PieceIdCreatorImpl(gamePieceValidator: GamePieceValidator, pieceIdGenerator: PieceIdGenerator) extends PieceIdCreator {
  override def create(gameId: String, newPieceCoordinate: PieceCoordinates, existingPieces: Seq[ChessGamePiecesDetails]): Either[ChessGameException, Int] = {
    existingPieces.find(existingPiece => gamePieceValidator.isGamePieceTaken(existingPiece = existingPiece, newPieceCoordinates = newPieceCoordinate)) match
      case Some(takenPiece) => Left(ChessPiecePlaceTaken(gameId = gameId, pieceId = takenPiece.pieceId, pieceCoordinates = newPieceCoordinate))
      case None => Right(pieceIdGenerator.generate(existingPieces))
  }
}

object PieceIdCreatorImpl {
  lazy val live = ZLayer.derive[PieceIdCreatorImpl]
}