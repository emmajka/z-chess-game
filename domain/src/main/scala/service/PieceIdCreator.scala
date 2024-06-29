package service

import exception.GameException
import model.{GamePiecesDetails, PieceCoordinates}

trait PieceIdCreator {
  def create(gameId: String, newPieceCoordinate: PieceCoordinates, gamePiecesDetails: Seq[GamePiecesDetails]): Either[GameException, Int]
}
