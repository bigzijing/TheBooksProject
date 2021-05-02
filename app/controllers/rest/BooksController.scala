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

  def getAll(): Action[AnyContent] = Action {
    if (booksList.isEmpty) {
      NoContent
    } else {
      Ok(Json.toJson(booksList))
    }
  }

}