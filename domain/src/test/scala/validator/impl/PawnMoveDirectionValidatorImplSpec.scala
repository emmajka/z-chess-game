package validator.impl

import model.PieceCoordinates
import zio.*
import zio.test.*

object PawnMoveDirectionValidatorImplSpec extends ZIOSpecDefault {
  val sut = PawnMoveDirectionValidatorImpl()

  val spec = suiteAll("PawnMoveDirectionValidator tests") {
    val testData = Seq(
      ((PieceCoordinates(1, 1), PieceCoordinates(1, 2)), true),
      ((PieceCoordinates(2, 1), PieceCoordinates(1, 1)), true),
      ((PieceCoordinates(3, 6), PieceCoordinates(6, 6)), true),
      ((PieceCoordinates(5, 2), PieceCoordinates(5, 4)), true),
      ((PieceCoordinates(5, 2), PieceCoordinates(3, 4)), false),
      ((PieceCoordinates(1, 2), PieceCoordinates(3, 4)), false),
      ((PieceCoordinates(1, 1), PieceCoordinates(2, 2)), false)
    )

    test("when pawn's movement is in straight line then it should return true") {
      assertTrue {
        testData.forall {
          case ((from, to), expected) =>
            sut.validate(from = from, to = to) == expected
        }
      }
    }
  }

}
