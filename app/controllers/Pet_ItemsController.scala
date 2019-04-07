package controllers

import javax.inject.Inject
import models.Pet_Item
import play.api.libs.json.{JsError, JsSuccess, Json, Writes}
import play.api.mvc._

//@Singleton
class Pet_ItemsController @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  val pet_shop = models.Pet_Shop

  // Json serializers
  implicit val readsPet_Item = Json.reads[Pet_Item]

  implicit val pet_ItemWrites = new Writes[Pet_Item] {
    def writes(pet: Pet_Item) = Json.obj(
      "id" -> pet.id,
      "name" -> pet.name,
      "price" -> pet.price
    )
  }

  val list = Action {
    //    Ok("List of results to come")
    //    Ok(Json.obj("result" -> "undefined", "message" -> "list results not implemented yet"))
    Ok(Json.toJson(pet_shop.list))
  }

  val create = Action(parse.json) { implicit request =>
    request.body.validate[Pet_Item] match {
      case JsSuccess(createItem, _) =>
        pet_shop.create(createItem.name, createItem.price) match {
          case Some(item) => Ok(Json.toJson(item))
          case None => InternalServerError
        }
      case JsError(errors) => BadRequest
    }
  }

  def details(id: Long) = Action {
    NotImplemented
  }

  def update(id: Long) = Action {
    NotImplemented
  }

  def delete(id: Long) = Action {
    NotImplemented
  }

}
