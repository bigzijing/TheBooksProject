package com.bigzij.scripts

import com.bigzij.mongo.{BooksServiceImpl, MongoModuleImpl}
import com.bigzij.mongo.models.BookDBO
import com.github.tototoshi.csv.CSVReader
import zio.{App, ExitCode, Task, URIO, ZEnv, ZIO}

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

object ImportGoogleSheetsLibrary extends App {

  override def run(args: List[String]): URIO[ZEnv, ExitCode] =
    (for {
      books <- booksZio
      _ <- insertBooksZio(books)
    } yield ()).exitCode

  implicit val ec = ExecutionContext.Implicits.global

  val booksZio = ZIO {
    val csvFile = CSVReader
      .open(io.Source.fromResource("csv/library_list.csv"))

    val csvLines = csvFile.all()
      .drop(2)

    csvLines.map(rowsToBook)
  }

  def insertBooksZio(books: List[BookDBO]): Task[Unit] = ZIO.fromFuture { implicit ec =>
    Future {
      val mongoModule = new MongoModuleImpl
      val bookService = new BooksServiceImpl(mongoModule)

      bookService.addBooks(books).onComplete(_ => mongoModule.closeMongoConnection(FiniteDuration(1, "SECONDS")))
    }
  }

  def rowsToBook(row: List[String]): BookDBO = {
    def emptyStringToNone(rawString: String): Option[String] =
      if (List("", "-").contains(rawString)) None
      else Some(rawString)

    def genresToList(genres: String): List[String] =
      genres.split("; ").toList

    BookDBO(
      title = row.head,
      isbn = row(10),
      author = row(1),
      publisher = row(2),
      category = genresToList(row(3)),
      language = row(4),
      cover = row(5),
      pages = emptyStringToNone(row(6)).map(_.toInt),
      condition = emptyStringToNone(row(7)),
      rating = emptyStringToNone(row(8)).map(_.toDouble),
      grRating = emptyStringToNone(row(9)).map(_.toDouble),
      location = emptyStringToNone(row(11)),
      status = emptyStringToNone(row(12)),
      notes = emptyStringToNone(row(13)),
      read = if (row(14) == "1") true else false,
      dateBought = None
    )
  }
}
