package com.github.fyfhust.jsontest

import spray.json._

// model
case class User(id: Int, name: String, password: String)

// Json converter
object UserJsonProtocol extends DefaultJsonProtocol {
  implicit val userJsonFormat = jsonFormat3(User)
}

object Test1 extends App {
  import UserJsonProtocol._
  val user = User(1, "fuyf", "passwd")
  val userJson: JsValue = user.toJson
  println(userJson.prettyPrint)
  println(userJson.compactPrint)
}
