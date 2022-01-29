package com.bigzij.mongo

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
import reactivemongo.api.{AsyncDriver, MongoConnection}
import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}
import zio.Console.printLine
import zio.{App, ExitCode, URIO, ZIO}
import Book._

import scala.concurrent.Future

object Main extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

    val booksDb = new MongoDSLImpl

    def getCollection(collectionName: String) = {
      val booksCollection: Future[BSONCollection] = booksDb.mongoDB.map(_.collection(collectionName))
      booksCollection
    }

    val testBook = Book(
      title = "Indiana Jones 151",
      isbn = "12345",
      author = "Josh King",
      publisher = "Penguin Publishing House",
      category = "Children's",
      language = "Ukrainian",
      cover = "Hard",
      pages = 14
    )

    val insertOne = getCollection("books")
      .flatMap { coll =>
        coll.insert.one(testBook)
      }

    ZIO.fromFuture(ec => insertOne).orDie *> printLine("Welcome to your first ZIO app!").exitCode
  }
}

case class Book(
                 title: String,
                 isbn: String,
                 author: String,
                 publisher: String,
                 category: String,
                 language: String,
                 cover: String,
                 pages: Int,
                 condition: Option[String] = None,
                 rating: Option[Double] = None,
                 grRating: Option[Double] = None,
                 location: Option[String] = None,
                 status: Option[String] = None,
                 notes: Option[String] = None,
                 read: Boolean = false
               )

object Book {
  implicit def bookWriter: BSONDocumentWriter[Book] = Macros.writer[Book]
  implicit def bookReader: BSONDocumentReader[Book] = Macros.reader[Book]
}
