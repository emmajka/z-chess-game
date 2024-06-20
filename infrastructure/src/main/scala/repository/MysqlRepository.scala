package repository

import io.getquill.*
import io.getquill.context.qzio.ImplicitSyntax.*
import zio.*

import java.sql.SQLException
import javax.sql.DataSource

trait MysqlRepository {
  val context: MysqlZioJdbcContext[SnakeCase]
  val datasource: DataSource
  import context._
  implicit val ds: Implicit[DataSource] = Implicit(datasource)
  
  inline final def executeQuery[T](inline q: Quoted[Query[T]]): IO[SQLException, List[T]] = run(q).implicitly
}
