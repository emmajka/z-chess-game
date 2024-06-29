package validator.impl

import model.PieceCoordinates
import zio.test.*

object PawnObstacleDetectorImplSpec extends ZIOSpecDefault {
  val sut = PawnObstacleDetectorImpl()

  val spec = suiteAll("PawnObstacleDetectorImpl tests") {
    val testData = Seq(
      ((PieceCoordinates(1, 1), PieceCoordinates(1, 3), PieceCoordinates(4, 4)), false),
      ((PieceCoordinates(1, 1), PieceCoordinates(1, 3), PieceCoordinates(1, 2)), true),
      ((PieceCoordinates(6, 6), PieceCoordinates(6, 1), PieceCoordinates(6, 2)), true),
      ((PieceCoordinates(6, 6), PieceCoordinates(6, 1), PieceCoordinates(2, 7)), false),
    )
    test("when no obstacle is detected in a straight line movement then it should return false") {
      assertTrue {
        testData.forall { case ((from, to, existing), expected) => sut.detect(from = from, to = to, existing = existing) == expected }
      }
    }
  }
}
