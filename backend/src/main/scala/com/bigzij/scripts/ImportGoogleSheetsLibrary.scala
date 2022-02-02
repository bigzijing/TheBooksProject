package com.bigzij.scripts

import com.bigzij.mongo.models.BookDBO
import com.github.tototoshi.csv.CSVReader
import zio.Console.printLine
import zio.{App, ExitCode, URIO, ZEnv, ZIO}

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

object ImportGoogleSheetsLibrary extends App {

  implicit val ec = ExecutionContext.Implicits.global

  val bufferedSource = io.Source.fromResource("csv/librarylist.csv")
    .getLines

  val csvFile = CSVReader
    .open(io.Source.fromResource("csv/librarylist.csv"))

  val csvLines = csvFile.all()
    .drop(2)
    .map(rowsToBook)

  def rowsToBook(row: List[String]): BookDBO = {
    def emptyStringToNone(rawString: String): Option[String] =
      if (List("", "-").contains(rawString)) None
      else Some(rawString)

    def genresToList(genres: String): List[String] =
      genres.split("\n").toList

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

  override def run(args: List[String]): URIO[ZEnv, ExitCode] =
    (printLine("hello world") *> ZIO.fromFuture(implicit ec => Future(csvLines.take(10).foreach(book => println(book.toShortString))))).exitCode
}
