package mysql.serde

import io.getquill.MappedEncoding
import model.PieceType
import model.PieceType.fromCode

object PieceType {

  implicit val quillEncode: MappedEncoding[PieceType, String] =
    MappedEncoding[PieceType, String](_.code)
  implicit val quillDecode: MappedEncoding[String, PieceType] =
    MappedEncoding[String, PieceType](fromCode)
}
