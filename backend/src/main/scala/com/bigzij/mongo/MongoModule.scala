package com.bigzij.mongo

import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

trait MongoModule {
  def mongoDB: Future[DB]

  def closeMongoConnection(timeout: FiniteDuration)
}

class MongoModuleImpl extends MongoModule {
  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  private val driver = AsyncDriver()

  override def closeMongoConnection(timeout: FiniteDuration) =
    driver.close(timeout)

  val mongoConfig = MongoConfig.fromConfig
  lazy val parsedURI = MongoConnection.fromString(mongoConfig.uri)
  lazy val connection = parsedURI.flatMap(u => driver.connect(u))

  def mongoDB: Future[DB] = connection.flatMap(_.database(mongoConfig.database))
}