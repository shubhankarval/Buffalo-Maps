/**
 * cse250.pa3.Main.scala
 *
 * Copyright 2020 Andrew Hughes (ahughes6@buffalo.edu)
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-nc-sa/4.0/.
 *
 * Modify at your leisure, but this will not be graded.
 */
package cse250.pa3

import cse250.objects.{StreetGraph, AssessmentUtilities}
import  scala.collection.mutable
object Main {

  def hash(key:String):Int={
    val l = key.length
    var sum = 0
    val map = Map[Char,Int]('a'->1,'b'->2,'c'->3,'d'->4,'e'->5,'f'->6,'g'->7,'h'->8,'i'->9,'j'->10,'k'->11,'l'->12,'m'->13,'n'->14,'o'->15,'p'->16,'q'->17,'r'->18,'s'->19,'t'->20,'u'->21,'v'->22,'w'->23,'x'->24,'y'->25,'z'->26)
    for(i <- 0 until l){
      sum += (Math.pow(2,i).toInt * map(key(i)))
    }
    sum
  }
  def main(args: Array[String]): Unit = {
    /*
    val taxentryFilename = "data/2017-2018_Assessment_Roll-updated-small.csv"
    val entries = AssessmentUtilities.loadAssessmentEntries(taxentryFilename, 25)
    val mapXMLFile = "data/buffalo-map"
    val intersectionNodeXMLFile = "data/export.osm"
    val intersectionIDs = MapUtilities.loadIntersectionIDs(intersectionNodeXMLFile)
    val nodeToStreetMapping = MapUtilities.loadMapInfo(mapXMLFile)
    val streetGraph = MapUtilities.buildIntersectionGraph(intersectionIDs, nodeToStreetMapping)
    println(s"${entries(1)} to\n${entries(2)}")
    println(MapUtilities.computeFewestTurns(streetGraph, entries(1), entries(2)))
    println(MapUtilities.computeFewestTurnsList(streetGraph, entries(1), entries(2)))

    println(s"${entries(3)} to\n${entries(24)}")
    println(MapUtilities.computeFewestTurns(streetGraph, entries(3), entries(24)))
    println(MapUtilities.computeFewestTurnsList(streetGraph, entries(3), entries(24)))
   */
    //MapUtilities.loadIntersectionIDs("data/testt.osm")
    //println(MapUtilities.loadMapInfo("data/test.xml"))
    println(hash("sem"))
  }
}
/*

  def computeFewestTurns(streetGraph: StreetGraph, start: TaxParcel, end: TaxParcel): Int = {
    val src = start.parcelInfo("STREET").toUpperCase
    val dest = end.parcelInfo("STREET").toUpperCase
    if(src==dest){
      return 0
    }

    var explored: Set[String] = Set(src)
    val toExplore = mutable.Queue[String]()
    toExplore.enqueue(src)

    val map = mutable.Map[String,Int](src->0)
    var distance = 0

    while (toExplore.nonEmpty) {
      val vertexToExplore = toExplore.dequeue()
      distance = map(vertexToExplore) + 1
      for (vertex <- streetGraph.vertices(vertexToExplore).edges) {
        if(!explored.contains(vertex.name)){
          if(vertex.name==dest){
            return distance
          }
          map(vertex.name) = distance
          toExplore.enqueue(vertex.name)
          explored += vertex.name
        }
      }
    }
    -1
  }


  def computeFewestTurns2(streetGraph: StreetGraph, start: TaxParcel, end: TaxParcel): Int = {
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
      return -1
    }
    var temp = dest
    var distance=0
    val x = true
    while(x) {
      if(temp==src){
        return distance
      }
      else {
        temp = map(temp)
        distance +=1
      }
    }
    -1
  }

 */


