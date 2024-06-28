package mysql

import io.getquill.*
import io.getquill.jdbczio.Quill
import zio.*

import javax.sql.DataSource

case class MysqlCtx() extends MysqlZioJdbcContext(SnakeCase)
object MysqlCtx       extends MysqlZioJdbcContext(SnakeCase)

case class MysqlConnection(ctx: MysqlCtx, ds: DataSource)

object MysqlConnection {
  lazy val ctx: ULayer[MysqlCtx]     = ZLayer.succeed(MysqlCtx())
  lazy val ds: TaskLayer[DataSource] = Quill.DataSource.fromPrefixClosable("datasource.mysql")

}
