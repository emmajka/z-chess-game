package repository

import io.getquill.*
import io.getquill.context.qzio.ImplicitSyntax.*
import model.ChessGameDetails
import mysql.schema.ChessGameTable
import zio.*

import javax.sql.DataSource

trait MysqlRepository {
  val context: MysqlZioJdbcContext[SnakeCase]
  val datasource: DataSource
  import context.*
  implicit val ds: Implicit[DataSource] = Implicit(datasource)
  
  inline final def executeSelect[T](inline select: Quoted[Query[T]]): IO[Exception, List[T]] = run(select).implicitly
  
  inline final def executeInsert[T,R](inline insert: Quoted[ActionReturning[T, R]]): IO[Exception, R] = run(insert).implicitly
  
}
