package com.bigzij.mongo

import reactivemongo.api.{Cursor, DB, ReadPreference, WriteConcern}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.{ExecutionContext, Future}

trait MongoDSL {
  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  protected def collection: Future[BSONCollection]

  protected def onError(error: Throwable): Unit

  def errorHandler[A](onError: Throwable => Unit): Cursor.ErrorHandler[A] =
    Cursor.ContOnError[A](
      (_, throwable) => onError(throwable)
    )

  def find[T: BSONDocumentReader](
    filter: BSONDocument,
    projection: Option[BSONDocument] = None,
    sort: Option[BSONDocument] = None,
    limit: Option[Int] = None,
    skip: Option[Int] = None,
    maxTimeMsOption: Option[Long] = None,
    readPreference: Option[ReadPreference] = None
  ): Future[List[T]] =
    collection.flatMap { coll =>
      val collection: BSONCollection = readPreference.map(coll.withReadPreference).getOrElse(coll)
      val cursor = collection.find(filter, projection)
      val cursorWithSkip = skip.map(cursor.skip(_)).getOrElse(cursor)
      val cursorWithMaxTimeMS = maxTimeMsOption.map(cursorWithSkip.maxTimeMs(_)).getOrElse(cursorWithSkip)
      val cursorWithSort = sort.map(cursorWithMaxTimeMS.sort(_)).getOrElse(cursorWithMaxTimeMS)
        .cursor[T]()

      cursorWithSort.collect[List](limit.getOrElse(-1), errorHandler(onError))
    }

  def findOne[T: BSONDocumentReader](
    filter: BSONDocument,
    projection: Option[BSONDocument] = None,
    maxTimeMsOption: Option[Long] = None,
    readPreference: Option[ReadPreference] = None
  ): Future[Option[T]] =
    collection.flatMap { coll =>
      val collection: BSONCollection = readPreference.map(coll.withReadPreference).getOrElse(coll)
      val cursor = collection.find(filter, projection)
      val cursorWithMaxTimeMS = maxTimeMsOption.map(cursor.maxTimeMs(_)).getOrElse(cursor)
        .cursor[T]()

      cursorWithMaxTimeMS.headOption
    }

  def delete(selector: BSONDocument): Future[WriteResult] =
    collection.flatMap(coll => coll.delete(ordered = false).one(selector))

  def deleteMany(selectors: BSONDocument): Future[BSONCollection#MultiBulkWriteResult] =
    collection.flatMap { coll =>
      val deleteBuilder = coll.delete(ordered = false)
      ???
    }

  def insert[T: BSONDocumentWriter](document: T): Future[WriteResult] =
    collection.flatMap(coll => coll.insert(ordered = false).one[T](document))

  def insertMany[T: BSONDocumentWriter](documents: Seq[T]): Future[BSONCollection#MultiBulkWriteResult] =
    collection.flatMap(coll => coll.insert(ordered = false).many[T](documents))

  def update(
    selector: BSONDocument,
    update: BSONDocument,
    upsert: Boolean = false,
    multi: Boolean = false
  ): Future[WriteResult] =
    collection.flatMap { coll =>
      coll
        .update(ordered = false, writeConcern = WriteConcern.Acknowledged)
        .one(q = selector, u = update, upsert = upsert, multi = multi)
    }

  def updateMany = ???
}