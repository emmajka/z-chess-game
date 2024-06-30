package validator.impl

import model.Position
import validator.PawnObstacleDetector

case class PawnObstacleDetectorImpl() extends PawnObstacleDetector {
  override def detect(from: Position, to: Position, existing: Position): Boolean =
    (from.x <= existing.x && existing.x <= to.x) || (from.y <= existing.y && existing.y <= to.y)
}
