package flow.impl

import flow.AddPieceFlow
import model.{ChessPieceType, PieceCoordinates}
import repository.ChessGameRepository
import service.PieceIdCreator
import zio.*

case class AddPieceFlowImpl(chessGameRepository: ChessGameRepository, pieceIdCreator: PieceIdCreator) extends AddPieceFlow {
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

object AddPieceFlowImpl {
  lazy val live = ZLayer.derive[AddPieceFlowImpl]
}
