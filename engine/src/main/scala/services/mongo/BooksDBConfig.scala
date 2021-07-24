package services.mongo

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Uri
import pureconfig.ConfigReader
import pureconfig.generic.semiauto._
import zio.config.refined._
import zio.config.magnolia.DeriveConfigDescriptor.descriptor

case class BooksDBConfig(booksDbUri: String Refined Uri)

object BooksDBConfig {
  implicit val configReader: ConfigReader[BooksDBConfig] = deriveReader[BooksDBConfig]

  val BooksDBConfigDescriptor: _root_.zio.config.ConfigDescriptor[BooksDBConfig] =
    descriptor[BooksDBConfig]
}
