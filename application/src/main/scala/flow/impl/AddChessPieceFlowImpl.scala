package flow.impl

import flow.AddChessPieceFlow
import model.{ChessPieceType, PieceCoordinates}
import repository.ChessGameRepository
import service.PieceIdCreator
import zio.*

case class AddChessPieceFlowImpl(chessGameRepository: ChessGameRepository, pieceIdCreator: PieceIdCreator) extends AddChessPieceFlow {
  override def run(gameId: String, pieceType: ChessPieceType, newPieceCoordinate: PieceCoordinates): Task[Unit] =
    for
      chessGameDetails <- chessGameRepository.getChessGameDetails(gameId = gameId)
      chessGamePiecesDetails <- chessGameRepository.getChessGamePiecesDetails(gameId = gameId)
      newPieceId <- ZIO.fromEither(
        pieceIdCreator.create(gameId = gameId, newPieceCoordinate = newPieceCoordinate, chessGamePiecesDetails = chessGamePiecesDetails)
      )
      _ <- chessGameRepository.createNewChessGamePiece(gameId = gameId, pieceId = newPieceId, pieceType = pieceType, coordinates = newPieceCoordinate)
    yield ()

}

object AddChessPieceFlowImpl {
  lazy val live = ZLayer.derive[AddChessPieceFlowImpl]
}
