package http.serde

import model.PieceType as PieceTypeModel
import sttp.tapir.*
import zio.json.*

object PieceType {
  given JsonEncoder[PieceTypeModel] = JsonEncoder[String].contramap(_.code)
  given JsonDecoder[PieceTypeModel] = JsonDecoder[String].map(PieceTypeModel.fromCode)
  given Schema[PieceTypeModel] = Schema.derivedEnumeration(encode = Some(_.code), schemaType =SchemaType.SString())
}
