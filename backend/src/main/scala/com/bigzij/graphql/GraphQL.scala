package com.bigzij.graphql

import caliban.GraphQL.graphQL
import caliban.RootResolver
import zio.{App, ExitCode, Task, URIO, ZEnv, ZIO}
import zio.Console._

object GraphQL extends App {
  override def run(args: List[String]) = {

    case class Book(title: String, isbn: String)

    def getBooks: List[Book] = List.empty[Book]
    def findByISBN(isbn: String): Book = Book(title = "Dummy Book", isbn = isbn)

    // schema
    case class BookISBN(isbn: String)
    case class Queries(books: List[Book], book: BookISBN => Book)

    // resolver
    val queries = Queries(getBooks, args => findByISBN(args.isbn))

    // api
    val api = graphQL(RootResolver(queries))

    val query =
      """
        |{
        |  books {
        |    isbn
        |  }
        |}
        |""".stripMargin

    (for {
      interpreter <- api.interpreter
      result <- interpreter.execute(query)
      _ <- printLine(result.data.toString)
    } yield ()).exitCode
  }

}
