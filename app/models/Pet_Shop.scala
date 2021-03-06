package models

import java.util.concurrent.atomic.AtomicLong

import scala.collection.concurrent.TrieMap

case class Pet_Item(id: Long, name: String, price: Double)

trait Pet_Franchise {
  def list(): Seq[Pet_Item]
  def create(name: String, price: Double): Option[Pet_Item]
  def get(id: Long): Option[Pet_Item]
  def update(id: Long, name: String, price: Double): Option[Pet_Item]
  def delete(id: Long): Boolean
}

object Pet_Shop extends Pet_Franchise {
  private val items = TrieMap.empty[Long, Pet_Item]
  private val seq = new AtomicLong

  def list(): Seq[Pet_Item] = items.values.to[Seq]

  def update(id: Long, name: String, price: Double): Option[Pet_Item] = {
    val item = Pet_Item(id, name, price)
    items.replace(id, item)
    Some(item)
  }

  def get(id: Long): Option[Pet_Item] = items.get(id)

  def delete(id: Long): Boolean = items.remove(id).isDefined

  def create(name: String, price: Double): Option[Pet_Item] = {
    val id = seq.incrementAndGet()
    val item = Pet_Item(id, name, price)
    items.put(id, item)
    Some(item)
  }
}