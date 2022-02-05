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

    val bigFuture =
      for {
        bookByOid <- bookService.findByOid("60fbff40a6ab18ee42d0e7e3")
        _ = println(s"The book you found by oid is $bookByOid\n")
        bookByIsbn <- bookService.findByISBN("12345")
        _ = println(s"The book you found by isbn is $bookByIsbn\n")
        bookByTitleAndAuthor <- bookService.findByTitleAndAuthor(Some("1Q84: The Complete Trilogy"), Some("Haruki Murakami"))
        _ = println(s"The book you found by title and author is $bookByTitleAndAuthor\n")
        bookByTitle <- bookService.findByTitleAndAuthor(Some("1Q84: The Complete Trilogy"), None)
        _ = println(s"The book you found by title is $bookByTitle\n")
        bookByAuthor <- bookService.findByTitleAndAuthor(None, Some("Haruki Murakami"))
        _ = println(s"The book you found by author is $bookByAuthor\n")
      } yield ()

    ZIO.fromFuture(implicit ec => bigFuture).orDie *> printLine("Welcome to your first ZIO app!").exitCode
  }
}

