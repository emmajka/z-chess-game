package service

import exception.ChessGameException
import model.{ChessGamePiecesDetails, PieceCoordinates}

trait PieceIdCreator {
  def create(gameId: String, newPieceCoordinate: PieceCoordinates, chessGamePiecesDetails: Seq[ChessGamePiecesDetails]): Either[ChessGameException, Int]
}
