package flow.impl

import exception.ChessGameException.ChessPieceDoesNotExists
import flow.DeleteChessPieceFlow
import repository.ChessGameRepository
import zio.*

case class DeleteChessPieceFlowImpl(chessGameRepository: ChessGameRepository) extends DeleteChessPieceFlow {
  override def run(gameId: String, pieceId: Int): Task[Unit] =
    for
      deletedRows <- chessGameRepository.deleteChessPiece(gameId = gameId, pieceId = pieceId)
      _ <- ZIO.fail(ChessPieceDoesNotExists(gameId = gameId, pieceId = pieceId)).when(deletedRows == 0)
    yield ()
}

object DeleteChessPieceFlowImpl {
  lazy val live = ZLayer.derive[DeleteChessPieceFlowImpl]
}
