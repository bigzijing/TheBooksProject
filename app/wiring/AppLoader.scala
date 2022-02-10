package wiring

import com.softwaremill.macwire.wire
import controllers.HomeController
import play.{ApplicationLoader, BuiltInComponentsFromContext}
import play.ApplicationLoader.Context
import play.api.http.DefaultHttpErrorHandler
import play.filters.components.{HttpFiltersComponents, NoHttpFiltersComponents}
import play.http.HttpErrorHandler
import play.mvc.{Http, Result}
import play.routing.Router
import router.Routes

import java.util.concurrent.CompletionStage

class AppLoader extends ApplicationLoader {
  def load(context: Context) = {
    val appComponents = new AppComponents(context)
    appComponents.application
  }
}

class AppComponents(context: ApplicationLoader.Context) extends BuiltInComponentsFromContext(context) with NoHttpFiltersComponents {
  println("HELLO WORLD")

  lazy val errorHandler = DefaultHttpErrorHandler

//  lazy val homeController = wire[HomeController]

  lazy val router = {
    val routePrefix: String = "/"
    wire[Routes]
  }
}

class CustomErrorHandler extends HttpErrorHandler {
  override def onClientError(request: Http.RequestHeader, statusCode: Int, message: String): CompletionStage[Result] = ???

  override def onServerError(request: Http.RequestHeader, exception: Throwable): CompletionStage[Result] = ???
}