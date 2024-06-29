package flow.impl

import exception.GameException.{GameNotExists, PiecePlaceTaken}
import flow.AddPieceFlow
import model.{PieceCoordinates, PieceType}
import repository.GameRepository
import zio.*

case class AddPieceFlowImpl(gameRepository: GameRepository) extends AddPieceFlow {
  override def run(gameId: String, pieceType: PieceType, newPieceCoordinates: PieceCoordinates): Task[Unit] =
    for
      // TODO change to single query instead
      gameDetails <- gameRepository.getGameDetails(gameId = gameId)
      _ <- ZIO.fail(GameNotExists(gameId = gameId)).when(gameDetails.isEmpty)
      gamePieces <- gameRepository.getGamePiecesDetails(gameId = gameId)
      pieceWithSamePosition = gamePieces.find(gp => gp.xCoordinate == newPieceCoordinates.x && gp.yCoordinate == newPieceCoordinates.y && gp.active)
      _ <- ZIO.foreachDiscard(pieceWithSamePosition)(p => ZIO.fail(PiecePlaceTaken(gameId = gameId, pieceId = p.pieceId, pieceCoordinates = newPieceCoordinates)))
      newPieceId = gamePieces.maxByOption(_.pieceId).map(_.pieceId + 1).getOrElse(1)
      _ <- gameRepository.addNewGamePiece(gameId = gameId, pieceId = newPieceId, pieceType = pieceType, coordinates = newPieceCoordinates)
    yield ()

}

object AddPieceFlowImpl {
  lazy val live = ZLayer.derive[AddPieceFlowImpl]
}
