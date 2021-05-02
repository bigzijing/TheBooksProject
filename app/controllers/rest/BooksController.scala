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

  def findBookByISBN(isbn: String): Action[AnyContent] = Action {
    tempLibrary.find(_.isbn.replaceAll("\\s", "") == isbn) match {
      case None => NotFound
      case Some(book) => Ok(Json.toJson(book))
    }
  }

  //TODO: Migrate

  implicit val booksJson = Json.format[Book]
  implicit val newBookJson = Json.format[NewBook]

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

  def addNewBook() = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson
    val bookItem: Option[NewBook] =
      jsonObject.flatMap(Json.fromJson[NewBook](_).asOpt)

    bookItem match {
      case None => BadRequest
      case Some(newBook) =>
        val newId = booksList.map(_.isbn).max + 1
        val toBeAdded = Book(newId, newBook.title, 0, None)
        booksList += toBeAdded
        Created(Json.toJson(toBeAdded))
    }
  }

}