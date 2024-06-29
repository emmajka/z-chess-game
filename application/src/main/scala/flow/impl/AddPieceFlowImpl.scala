package flow.impl

import flow.AddPieceFlow
import model.{PieceCoordinates, PieceType}
import repository.GameRepository
import service.{GameService, PieceIdCreator}
import zio.*

case class AddPieceFlowImpl(gameRepository: GameRepository, pieceIdCreator: PieceIdCreator, gameService: GameService) extends AddPieceFlow {
  override def run(gameId: String, pieceType: PieceType, newPieceCoordinate: PieceCoordinates): Task[Unit] =
    for
      gamePiecesDetails <- gameService.getGameDetails(gameId = gameId)
      newPieceId <- ZIO.fromEither(
        pieceIdCreator.create(gameId = gameId, newPieceCoordinate = newPieceCoordinate, gamePiecesDetails = gamePiecesDetails)
      )
      _ <- gameRepository.addNewGamePiece(gameId = gameId, pieceId = newPieceId, pieceType = pieceType, coordinates = newPieceCoordinate)
    yield ()

}

object AddPieceFlowImpl {
  lazy val live = ZLayer.derive[AddPieceFlowImpl]
}
