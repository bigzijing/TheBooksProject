package com.bigzij.mongo

import com.bigzij.mongo.models.BookDBO
import reactivemongo.api.DB
import reactivemongo.api.bson.{BSONDocument, BSONObjectID}
import reactivemongo.api.bson.collection.BSONCollection
import zio.ZIO

import java.time.Instant
import scala.concurrent.Future

trait BooksService {
  def findByOid(oid: String): Future[Option[BookDBO]]

  def findByISBN(isbn: String): Future[List[BookDBO]]

  def findByTitleAndAuthor(title: Option[String], author: Option[String]): Future[List[BookDBO]]

  def findBook(
              _id: Option[String],
              title: Option[String],
              isbn: Option[String],
              author: Option[String],
              publisher: Option[String],
              category: Option[String],
              cover: Option[String],
              pages: Option[Int],
              condition: Option[String],
              rating: Option[Double],
              grRating: Option[Double],
              location: Option[String],
              status: Option[String],
              note: Option[String],
              read: Option[Boolean]
              ): Future[List[BookDBO]]

  def addBook(book: BookDBO): Future[Unit]

  def addBooks(books: List[BookDBO]): Future[Unit]
}

class BooksServiceImpl(mongoService: MongoModule, collectionName: String = "books") extends BooksService with MongoDSL {
  implicit val ec2: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  override protected def onError(error: Throwable): Unit = println(s"Error encountered: ${error.getMessage}")

  val collection: Future[BSONCollection] = mongoService.mongoDB.map(_.collection(collectionName))

  override def findByOid(oid: String): Future[Option[BookDBO]] =
    findOne[BookDBO](
      BSONDocument("_id" -> s"ObjectId('$oid')")
    )

  override def findByISBN(isbn: String): Future[List[BookDBO]] =
    find[BookDBO](
      BSONDocument("isbn" -> isbn)
    )

  override def findByTitleAndAuthor(title: Option[String] = None, author: Option[String] = None): Future[List[BookDBO]] =
    if (title.isEmpty && author.isEmpty) Future(List.empty[BookDBO])
    else find[BookDBO] {
      val query = List(
        title.map(t => BSONDocument("title" -> t)),
        author.map(a => BSONDocument("author" -> a))
      ).flatten

      BSONDocument("$or" -> query)
    }

  override def findBook(_id: Option[String], title: Option[String], isbn: Option[String], author: Option[String], publisher: Option[String], category: Option[String], cover: Option[String], pages: Option[Int], condition: Option[String], rating: Option[Double], grRating: Option[Double], location: Option[String], status: Option[String], note: Option[String], read: Option[Boolean]): Future[List[BookDBO]] = ???

  def addBook(book: BookDBO): Future[Unit] = this.insert(book.copy(createdDate = Some(Instant.now()))).map(_ => ())

  def addBooks(books: List[BookDBO]): Future[Unit] =
    this
      .insertMany(books.map(book => book.copy(createdDate = Some(Instant.now()))))
      .map(_ => ())
}
