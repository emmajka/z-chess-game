package validator.impl

import model.PieceCoordinates
import validator.PawnObstacleDetector

case class PawnObstacleDetectorImpl() extends PawnObstacleDetector {
  override def detect(from: PieceCoordinates, to: PieceCoordinates, existing: PieceCoordinates): Boolean =
    (from.x <= existing.x && existing.x <= to.x) || (from.y <= existing.y && existing.y <= to.y)
}
