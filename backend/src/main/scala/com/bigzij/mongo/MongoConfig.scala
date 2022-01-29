package com.bigzij.mongo

import pureconfig.{ConfigReader, ConfigSource}
import pureconfig.generic.semiauto.deriveReader

case class MongoConfig(uri: String, database: String)

object MongoConfig {
  implicit val configReader: ConfigReader[MongoConfig] = deriveReader[MongoConfig]
  def fromConfig: MongoConfig = ConfigSource.default.at("mongo").loadOrThrow[MongoConfig]
}
