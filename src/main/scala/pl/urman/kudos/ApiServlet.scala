package pl.urman.kudos

import org.scalatra._
import pl.urman.kudos.model.kudo._
import org.json4s.{ DefaultFormats, Formats }
import org.scalatra.json._

class ApiServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/kudos") {
    val repo = new KudosRepo()
    val k = repo.get()
    k
  }
  
  post("/kudos") {
    val kudos = parsedBody.extract[Kudo];
    val repo = new KudosRepo()
    repo.store(kudos);
  }
}
