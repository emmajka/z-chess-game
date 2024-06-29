package repository

import io.getquill.*
import io.getquill.context.qzio.ImplicitSyntax.*

import javax.sql.DataSource

trait MysqlRepository {
  val context: MysqlZioJdbcContext[SnakeCase]
  val datasource: DataSource
  implicit val ds: Implicit[DataSource] = Implicit(datasource)
}
