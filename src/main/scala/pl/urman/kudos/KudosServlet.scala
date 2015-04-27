package pl.urman.kudos

import org.scalatra._

class KudosServlet extends KudosStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        <p>Go to Kudos page <a href="main">Kudos Page</a>.</p>
      </body>
    </html>
  }

}
