package flow

import model.{PieceCoordinates, PieceType}
import zio.Task

trait AddPieceFlow {
  def run(gameId: String, pieceType: PieceType, pieceCoordinates: PieceCoordinates): Task[Unit]
}
