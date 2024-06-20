package mysql

import io.getquill.jdbczio.Quill
import io.getquill.{MysqlZioJdbcContext, SnakeCase}
import zio.{TaskLayer, ULayer, ZLayer}

import java.io.Closeable
import javax.sql.DataSource

case class MysqlCtx() extends MysqlZioJdbcContext(SnakeCase)
//object MysqlCtx extends MysqlZioJdbcContext(SnakeCase)

case class MysqlConnection(ctx: MysqlCtx, ds: DataSource)

object MysqlConnection {
  lazy val ctx: ULayer[MysqlCtx]                    = ZLayer.succeed(MysqlCtx())
  lazy val ds: TaskLayer[DataSource with Closeable] = Quill.DataSource.fromPrefixClosable("datasource.mysql")

}
