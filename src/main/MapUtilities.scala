/**
 * cse250.pa3.MapUtilities.scala
 *
 * Copyright 2020 Andrew Hughes (ahughes6@buffalo.edu)
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-nc-sa/4.0/.
 *
 * Submission author
 * UBIT:skvalimb
 * Person#:50305613
 *
 * Collaborators (include UBIT name of each, comma separated):
 * UBIT:skvalimb
 * Citation:  https://github.com/hartloff/CSE116-Examples/blob/master/src/week9/BFS.scala
 * Slightly altered bfs method from CSE 116
 */
package cse250.pa3

import cse250.objects.{StreetGraph, TaxParcel}

import scala.collection.mutable
import scala.xml.XML

object MapUtilities {
  def loadIntersectionIDs(filename: String): mutable.Set[String] = {
    val xml = XML.loadFile(filename)
    val nodeSeq = xml \\ "@id"
    val idSet = nodeSeq.to(mutable.Set).map(_.text)
    println(idSet)
    idSet
  }

  def loadMapInfo(filename: String): mutable.Map[String, mutable.Set[String]] = {
    val map = mutable.Map[String, mutable.Set[String]]()
    val xml = XML.loadFile(filename)
    val nodeSeq = xml \ "way"
    for(node<-nodeSeq){
      var street = ""
      val tags = node \ "tag"
      for(tag <- tags){
        if((tag \ "@k").text == "tiger:name_base"){
          street = (tag \ "@v").text
        }
      }
      if(street.nonEmpty){
        val idList = node \ "nd"
        for(id <- idList){
          val ref = (id \ "@ref").text
          if(map.contains(ref)){
            map(ref) += street
          }
          else{
            map(ref) = mutable.Set[String](street)
          }
        }
      }
    }
    map
  }

  def buildIntersectionGraph(intersectionIDs: mutable.Set[String],
                             nodeToStreetMapping: mutable.Map[String, mutable.Set[String]]): StreetGraph = {
    val streetGraph = new StreetGraph
    for((id,set) <- nodeToStreetMapping){
      if(intersectionIDs.contains(id)){
        for(src <- set){
          for(dest <- set){
            if(src != dest){
              streetGraph.insertEdge(src.toUpperCase, dest.toUpperCase)
            }
          }
        }
      }
    }
    streetGraph
  }

  def computeFewestTurns(streetGraph: StreetGraph, start: TaxParcel, end: TaxParcel): Int = {
    val src = start.parcelInfo("STREET").toUpperCase
    val dest = end.parcelInfo("STREET").toUpperCase
    if(src==dest){
      return 0
    }
    if(!streetGraph.vertices.contains(src) || !streetGraph.vertices.contains(dest)){
      return -1
    }
    var explored: Set[String] = Set(src)
    val toExplore = mutable.Queue[String]()
    toExplore.enqueue(src)
    val map = mutable.Map[String,Int](src->0)
    while (toExplore.nonEmpty) {
      val vertexToExplore = toExplore.dequeue()
      val distance = map(vertexToExplore) + 1
      for (vertex <- streetGraph.vertices(vertexToExplore).edges) {
        if(!explored.contains(vertex.name)){
          map(vertex.name) = distance
          toExplore.enqueue(vertex.name)
          explored += vertex.name
        }
      }
    }
    if(explored.contains(dest)){
      return map(dest)
    }
    -1
  }

  def computeFewestTurnsList(streetGraph: StreetGraph, start: TaxParcel, end: TaxParcel): Seq[String] = {
    val src = start.parcelInfo("STREET").toUpperCase
    val dest = end.parcelInfo("STREET").toUpperCase
    if(src==dest){
      return List(src)
    }

    if(!streetGraph.vertices.contains(src) || !streetGraph.vertices.contains(dest)){
      return List[String]()
    }
    var explored: Set[String] = Set(src)
    val toExplore = mutable.Queue[String]()
    toExplore.enqueue(src)

    val map = mutable.Map[String,String]()

    while (toExplore.nonEmpty) {
      val vertexToExplore = toExplore.dequeue()
      for (vertex <- streetGraph.vertices(vertexToExplore).edges) {
        if(!explored.contains(vertex.name)){
          map(vertex.name) = vertexToExplore
          toExplore.enqueue(vertex.name)
          explored += vertex.name
        }
      }
    }
    if(!explored.contains(dest)){
      return List[String]()
    }
    var temp = dest
    var lst = List[String]()
    val x = true
    while(x) {
      if(temp==src){
        lst = lst :+ src
        return lst.reverse
      }
      else {
        lst = lst :+ temp
        temp = map(temp)
      }
    }
    lst
  }

}




