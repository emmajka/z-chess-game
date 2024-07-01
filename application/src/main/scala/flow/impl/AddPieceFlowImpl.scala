package flow.impl

import exception.GameException.{GameNotExists, PiecePlaceTaken}
import flow.AddPieceFlow
import model.{Position, PieceType}
import repository.GameRepository
import zio.*

case class AddPieceFlowImpl(gameRepository: GameRepository) extends AddPieceFlow {
  override def run(gameId: String, pieceType: PieceType, targetPosition: Position): Task[Int] =
    for
      gameDetails <- gameRepository.getGameDetails(gameId = gameId).filterOrFail(_.nonEmpty)(GameNotExists(gameId = gameId))
      gamePieces <- gameRepository.getGamePiecesDetails(gameId = gameId)
      pieceWithSamePosition = gamePieces.find(gp => gp.xCoordinate == targetPosition.x && gp.yCoordinate == targetPosition.y && gp.active)
      _ <- ZIO.foreachDiscard(pieceWithSamePosition)(p => ZIO.fail(PiecePlaceTaken(gameId = gameId, pieceId = p.pieceId, pieceCoordinates = targetPosition)))
      newPieceId = gamePieces.maxByOption(_.pieceId).map(_.pieceId + 1).getOrElse(1)
      _ <- gameRepository.addNewGamePiece(gameId = gameId, pieceId = newPieceId, pieceType = pieceType, coordinates = targetPosition)
    yield newPieceId

}

object AddPieceFlowImpl {
  lazy val live = ZLayer.derive[AddPieceFlowImpl]
}
