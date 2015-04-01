package pl.urman.kudos

import org.scalatra._
import pl.urman.kudos.model.kudo.Kudo
import org.json4s.{ DefaultFormats, Formats }
import org.scalatra.json._

class ApiServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/kudos") {
    new Kudo("person@gmail.com", "You are awsome")
  }
  
  post("/kudos") {
    val kudos = parsedBody.extract[Kudo];
  }
}
