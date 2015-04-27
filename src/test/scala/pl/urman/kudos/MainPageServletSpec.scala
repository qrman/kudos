package pl.urman.kudos

import org.scalatra.test.specs2._

class MainPageServletSpec extends ScalatraSpec {
  def is =
    "GET / on KudosServlet" ^
      "should return status 200" ! root200 ^
      end

  addServlet(classOf[MainPageServlet], "/*")

  def root200 = get("/") {
    status must_== 200
  }
}
