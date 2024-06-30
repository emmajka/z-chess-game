package validator.impl

import model.Position
import zio.test.*

object PawnObstacleDetectorImplSpec extends ZIOSpecDefault {
  val sut = PawnObstacleDetectorImpl()

  val spec = suiteAll("PawnObstacleDetectorImpl tests") {
    val testData = Seq(
      ((Position(1, 1), Position(1, 3), Position(4, 4)), false),
      ((Position(1, 1), Position(1, 3), Position(1, 2)), true),
      ((Position(6, 6), Position(6, 1), Position(6, 2)), true),
      ((Position(6, 6), Position(6, 1), Position(2, 7)), false),
    )
    test("when no obstacle is detected in a straight line movement then it should return false") {
      assertTrue {
        testData.forall { case ((from, to, existing), expected) => sut.detect(from = from, to = to, existing = existing) == expected }
      }
    }
  }
}
