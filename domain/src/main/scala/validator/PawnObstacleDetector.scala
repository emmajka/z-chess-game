package validator

import model.PieceCoordinates

trait PawnObstacleDetector {
  def detect(from: PieceCoordinates, to: PieceCoordinates, existing: PieceCoordinates): Boolean
}
