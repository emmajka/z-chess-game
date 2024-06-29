package flow.impl

import exception.GameException.PieceNotExists
import flow.DeletePieceFlow
import repository.GameRepository
import zio.*

case class DeletePieceFlowImpl(gameRepository: GameRepository) extends DeletePieceFlow {
  override def run(gameId: String, pieceId: Int): Task[Unit] =
    for
      deletedRows <- gameRepository.deleteGamePiece(gameId = gameId, pieceId = pieceId)
      _ <- ZIO.fail(PieceNotExists(gameId = gameId, pieceId = pieceId)).when(deletedRows == 0)
    yield ()
}

object DeletePieceFlowImpl {
  lazy val live = ZLayer.derive[DeletePieceFlowImpl]
}
