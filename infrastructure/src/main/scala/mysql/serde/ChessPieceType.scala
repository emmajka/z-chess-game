package mysql.serde

import io.getquill.MappedEncoding
import model.ChessPieceType
import model.ChessPieceType.fromCode

object ChessPieceType {

  implicit val quillEncode: MappedEncoding[ChessPieceType, String] =
    MappedEncoding[ChessPieceType, String](_.code)
  implicit val quillDecode: MappedEncoding[String, ChessPieceType] =
    MappedEncoding[String, ChessPieceType](fromCode)
}
