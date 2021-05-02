package controllers.rest

import javax.inject._
import models.Book
import play.api._
import play.api.libs.json._
import play.api.mvc._

import scala.collection.mutable

@Singleton
class BooksController @Inject()(val controllerComponents: ControllerComponents) extends BaseController{

  implicit val booksJson = Json.format[Book]

  private val booksList = new mutable.ListBuffer[Book]()
  booksList += Book(1501115073, "My Grandmother Sends Her Regards and Apologizes")
  booksList += Book(9780571272136L, "Never Let Me Go")
  booksList += Book(9780747532743L, "Harry Potter and the Philosopher's Stone", description = Some("Harry Potter discovers on his 10th birthday that he is a wizard."))

  def getAll(): Action[AnyContent] = Action {
    if (booksList.isEmpty) {
      NoContent
    } else {
      Ok(Json.toJson(booksList))
    }
  }

  def getByISBN(ISBN: Long): Action[AnyContent] = Action {
    val foundBook = booksList.find(_.isbn == ISBN)
    foundBook match {
      case Some(book) => Ok(Json.toJson(book))
      case None => NotFound
    }
  }

  def viewBook(ISBN: Long): Action[AnyContent] = Action {
    val foundBook = booksList.find(_.isbn == ISBN)
    foundBook match {
      case None => NotFound
      case Some(book) => {
        val updatedBook = book.copy(view = book.view + 1)
        booksList.update(booksList.indexOf(book), updatedBook)
        Ok(Json.toJson(updatedBook))
      }
    }
  }

  def deleteAll(): Action[AnyContent] = Action {
    booksList.clear()
    Ok(Json.toJson(booksList))
  }

}