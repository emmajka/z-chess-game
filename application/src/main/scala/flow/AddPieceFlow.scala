package flow

import model.{ChessPieceType, PieceCoordinates}
import zio.Task

trait AddPieceFlow {
  def run(gameId: String, pieceType: ChessPieceType, pieceCoordinates: PieceCoordinates): Task[String]
}
