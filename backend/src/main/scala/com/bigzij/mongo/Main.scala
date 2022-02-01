package com.bigzij.mongo

import com.bigzij.mongo.models.BookDBO
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
import reactivemongo.api.{AsyncDriver, MongoConnection}
import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}
import zio.Console.printLine
import zio.{App, ExitCode, URIO, ZIO}

import scala.concurrent.Future

object Main extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

    val mongoModule = new MongoModuleImpl
    val bookService = new BooksServiceImpl(mongoModule)

    val booksDb = new MongoModuleImpl

    def getCollection(collectionName: String) = {
      val booksCollection: Future[BSONCollection] = booksDb.mongoDB.map(_.collection(collectionName))
      booksCollection
    }

    val testBook = BookDBO(
      title = "Indiana Jones 151",
      isbn = "12345",
      author = "Josh King",
      publisher = "Penguin Publishing House",
      category = "Children's",
      language = "Ukrainian",
      cover = "Hard",
      pages = 14
    )

    val insertOneAgain = getCollection("books")
      .flatMap { coll =>
        coll.insert.one(testBook)
      }

    val insertOne = bookService.insertOne(testBook)
    val insertOne2 = bookService.insertOne2(testBook)

    (ZIO(insertOne) *> ZIO(insertOne2) *> ZIO.fromFuture(ec => insertOneAgain)).orDie *> printLine("Welcome to your first ZIO app!").exitCode
  }
}

