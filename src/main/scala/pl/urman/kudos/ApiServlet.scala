package pl.urman.kudos

import org.scalatra._
import pl.urman.kudos.model.kudo._
import org.json4s.{ DefaultFormats, Formats }
import org.scalatra.json._

class ApiServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  val repo = new KudosRepo()
  
  before() {
    contentType = formats("json")
  }

  get("/kudos/:user") {
    val kudosList = repo.get(params("user"))
    kudosList
  }
  
  post("/kudos") {
    val kudos = parsedBody.extract[Kudo];
    repo.store(kudos);
  }
}
