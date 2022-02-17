package wiring

import com.softwaremill.macwire.wire
import _root_.controllers.AssetsComponents
import controllers.HomeController
import controllers.rest.BooksController
import play.api.{ApplicationLoader, BuiltInComponentsFromContext}
import play.api.ApplicationLoader.Context
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.ControllerComponents
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import play.http.HttpErrorHandler
import play.mvc.{Http, Result}
import router.Routes

import java.util.concurrent.CompletionStage

class AppLoader extends ApplicationLoader {

  def load(context: Context) = new AppComponents(context).application
}

trait AppModule {
  lazy val homeController = wire[HomeController]

  lazy val booksController = wire[BooksController]

  def controllerComponents: ControllerComponents
}

class AppComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
  with AppModule
  with AssetsComponents
  with HttpFiltersComponents {
  println("HELLO WORLD")

  lazy val errorHandler = DefaultHttpErrorHandler

  lazy val router: Router = {
    val prefix: String = "/"
    wire[Routes]
  }
}

class CustomErrorHandler extends HttpErrorHandler {

  override def onClientError(request: Http.RequestHeader, statusCode: Int, message: String): CompletionStage[Result] =
    ???

  override def onServerError(request: Http.RequestHeader, exception: Throwable): CompletionStage[Result] = ???
}
