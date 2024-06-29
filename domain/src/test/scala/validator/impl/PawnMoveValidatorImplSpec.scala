package validator.impl

import model.PieceCoordinates
import zio.*
import zio.test.*

object PawnMoveValidatorImplSpec extends ZIOSpecDefault {
  val sut = PawnMoveValidatorImpl()

  val spec = suiteAll("PawnMoveValidator tests ") {
    val happyPathTestData = Seq(
      (PieceCoordinates(1, 1), PieceCoordinates(1, 2), PieceCoordinates(3, 3)),
      (PieceCoordinates(3, 3), PieceCoordinates(3, 5), PieceCoordinates(5, 2)),
      (PieceCoordinates(8, 8), PieceCoordinates(8, 1), PieceCoordinates(1, 7))
    )

    test("when no obstacle occurs on piece's straight path then it should return a successful result") {
      assertTrue {
        happyPathTestData.forall {
          case (from, to, existing) =>
            sut.validate(moveFrom = from, moveTo = to, existingPiecePosition = existing).isRight
        }
      }
    }
//    val nonStraightMoveTestData = Seq(
//      (PieceCoordinates(1, 1), PieceCoordinates(3, 2), PieceCoordinates(1, 1)),
//      (PieceCoordinates(3, 3), PieceCoordinates(3, 5), PieceCoordinates(1, 1)),
//      (PieceCoordinates(8, 8), PieceCoordinates(8, 1), PieceCoordinates(1, 1))
//    )
//    test ("when move is not in straight line then it should return specific exception"){
//      
//    }
  }
}
