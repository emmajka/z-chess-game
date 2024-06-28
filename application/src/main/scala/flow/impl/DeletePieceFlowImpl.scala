package flow.impl

import exception.ChessGameException.ChessPieceDoesNotExists
import flow.DeletePieceFlow
import repository.ChessGameRepository
import zio.*

case class DeletePieceFlowImpl(chessGameRepository: ChessGameRepository) extends DeletePieceFlow {
  override def run(gameId: String, pieceId: Int): Task[Unit] =
    for
      deletedRows <- chessGameRepository.deleteChessPiece(gameId = gameId, pieceId = pieceId)
      _ <- ZIO.fail(ChessPieceDoesNotExists(gameId = gameId, pieceId = pieceId)).when(deletedRows == 0)
    yield ()
}

object DeletePieceFlowImpl {
  lazy val live = ZLayer.derive[DeletePieceFlowImpl]
}
