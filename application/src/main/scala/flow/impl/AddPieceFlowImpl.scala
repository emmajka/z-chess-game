package flow.impl

import flow.AddPieceFlow
import model.{ChessPieceType, PieceCoordinates}
import repository.ChessGameRepository
import zio.Task

case class AddPieceFlowImpl(chessGameRepository: ChessGameRepository) extends AddPieceFlow {
  override def run(gameId: String, pieceType: ChessPieceType, pieceCoordinates: PieceCoordinates): Task[String] = ???
}
