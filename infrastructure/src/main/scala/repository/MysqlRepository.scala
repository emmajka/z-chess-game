package repository

import io.getquill.*
import io.getquill.context.qzio.ImplicitSyntax.*
import zio.*

import javax.sql.DataSource

trait MysqlRepository {
  val context: MysqlZioJdbcContext[SnakeCase]
  val datasource: DataSource
  import context.*
  implicit val ds: Implicit[DataSource] = Implicit(datasource)
  
  inline final def executeSelect[T](inline select: Quoted[Query[T]]): IO[Exception, Seq[T]] = run(select).implicitly
  
  inline final def executeInsert[T](inline insert: Insert[T]): IO[Exception, Long] = run(insert).implicitly
  
}
