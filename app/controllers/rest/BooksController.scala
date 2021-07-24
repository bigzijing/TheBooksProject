package controllers.rest

import javax.inject._
import models.{Book, NewBook, RealBook}
import play.api._
import play.api.libs.json._
import play.api.mvc._

import scala.collection.mutable

@Singleton
class BooksController @Inject()(val controllerComponents: ControllerComponents) extends BaseController{

  implicit val realBooksJson = Json.format[RealBook]
  import models.TemporaryLibrary.tempLibrary

  def getAllBooks(): Action[AnyContent] = Action {
    if (tempLibrary.isEmpty) {
      NoContent
    } else {
      Ok(Json.toJson(tempLibrary))
    }
  }

  def findBookByISBN(isbn: String): Action[AnyContent] =
    findBy(
      tempLibrary, isbn
    )(
      _.isbn.replaceAll("\\s", ""),
      isbn => isbn
    )

  def findBookByTitle(title: String): Action[AnyContent] =
    findBy(
      tempLibrary, title
    )(
      _.title.toLowerCase.replaceAll("\\s", ""),
      _.toLowerCase
    )

  def findBooksByAuthor(author: String): Action[AnyContent] = Action {
    val matches = tempLibrary
      .collect {
        case book @RealBook(_, _, bookAuthor, _, _, _, _, _, _, _, _, _, _, _, _) if {
          bookAuthor.toLowerCase.split(" ").exists(author.contains) && author.length > 3
        } =>
          book
      }
    if (matches.nonEmpty) Ok(Json.toJson(matches)).as("application/json")
    else NotFound
  }

  def findBy[A, B](collection: mutable.ListBuffer[RealBook], identifier: A)(f1: RealBook => B, f2: A => B): Action[AnyContent] = Action {
    collection.find(f1(_) == f2(identifier)) match {
      case None => NotFound
      case Some(book) => Ok(Json.toJson(book))
    }
  }

//  def deleteAll(): Action[AnyContent] = Action {
//    booksList.clear()
//    Ok(Json.toJson(booksList))
//  }
//
//  def addNewBook() = Action { implicit request =>
//    val content = request.body
//    val jsonObject = content.asJson
//    val bookItem: Option[NewBook] =
//      jsonObject.flatMap(Json.fromJson[NewBook](_).asOpt)
//
//    bookItem match {
//      case None => BadRequest
//      case Some(newBook) =>
//        val newId = booksList.map(_.isbn).max + 1
//        val toBeAdded = Book(newId, newBook.title, 0, None)
//        booksList += toBeAdded
//        Created(Json.toJson(toBeAdded))
//    }
//  }

}