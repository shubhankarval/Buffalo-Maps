/**
 * cse250.pa3.tests.MapUtilityTests.scala
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
 */
package cse250.pa3.tests

import cse250.objects.TaxParcel
import org.scalatest.{BeforeAndAfter, FlatSpec}

import scala.collection.mutable
import cse250.pa3.MapUtilities
import cse250.objects.StreetGraph


class MapUtilityTests extends FlatSpec with BeforeAndAfter {
  // Your tests for problem 1 should be contained under this header.
  behavior of "MapUtilityTests 1(a)"
  it should "..." in {
    val map = mutable.Map[String,mutable.Set[String]]("1"->mutable.Set("A","B","C","D"), "2"->mutable.Set("A","E"), "3"->mutable.Set("B","F"), "4"->mutable.Set("C","H"), "5"->mutable.Set("D","G"), "6"->mutable.Set("I"), "7"->mutable.Set("J","K"))
    val idSet = mutable.Set[String]("1","2","3","4","5")
    val graph = MapUtilities.buildIntersectionGraph(idSet,map)
    //checks length of vertices map
    assert(graph.vertices.size == 8)
    //checks if all vertices are present in vertices map
    val vSet = graph.vertices.keys.toSet
    assert(vSet.contains("A") && vSet.contains("B") && vSet.contains("C") && vSet.contains("D") && vSet.contains("E") && vSet.contains("F") && vSet.contains("G") && vSet.contains("H"))
    //checks if all edges are present in edges set
    val eSet = Set[(String,String)](("A","B"),("A","C"),("A","D"),("B","A"),("B","C"),("B","D"),("C","A"),("C","B"),("C","D"),("D","A"),("D","B"),("D","C"),("A","E"),("E","A"),("B","F"),("F","B"),("C","H"),("H","C"),("D","G"),("G","D"))
    for(edge <- eSet){
      assert(graph.edges.contains(edge))
    }
    //checks edges of each vertex obj
    val vMap = graph.vertices
    for(v<-vMap){
      if(v._1=="A"){
        assert(v._2.edges.contains(vMap("B")) && v._2.edges.contains(vMap("C")) && v._2.edges.contains(vMap("D")) && v._2.edges.contains(vMap("E")))
      }
      if(v._1=="B"){
        assert(v._2.edges.contains(vMap("A")) && v._2.edges.contains(vMap("C")) && v._2.edges.contains(vMap("D")) && v._2.edges.contains(vMap("F")))
      }
      if(v._1=="C"){
        assert(v._2.edges.contains(vMap("A")) && v._2.edges.contains(vMap("B")) && v._2.edges.contains(vMap("D")) && v._2.edges.contains(vMap("H")))
      }
      if(v._1=="D"){
        assert(v._2.edges.contains(vMap("A")) && v._2.edges.contains(vMap("B")) && v._2.edges.contains(vMap("C")) && v._2.edges.contains(vMap("G")))
      }
      if(v._1=="E"){
        assert(v._2.edges.contains(vMap("A")))
      }
      if(v._1=="F"){
        assert(v._2.edges.contains(vMap("B")))
      }
      if(v._1=="G"){
        assert(v._2.edges.contains(vMap("D")))
      }
      if(v._1=="H"){
        assert(v._2.edges.contains(vMap("C")))
      }
    }
    assert(graph.edges.size == eSet.size)
  }

  behavior of "MapUtilityTests 1(b)"
  it should "..." in {
    val map = mutable.Map[String,mutable.Set[String]]("1"->mutable.Set("A","B","C","D"), "2"->mutable.Set("A","E"), "3"->mutable.Set("B","F"), "4"->mutable.Set("C","H"), "5"->mutable.Set("D","G"), "6"->mutable.Set("I"), "7"->mutable.Set("J","K"))
    val idSet = mutable.Set[String]("1","2","3","4","5")
    val graph = MapUtilities.buildIntersectionGraph(idSet,map)
    val a = new TaxParcel
    a.parcelInfo("STREET") = "a"
    val b = new TaxParcel
    b.parcelInfo("STREET") = "b"
    val e = new TaxParcel
    e.parcelInfo("STREET") = "e"
    val f = new TaxParcel
    f.parcelInfo("STREET") = "f"
    val i = new TaxParcel
    i.parcelInfo("STREET") = "i"
    assert(MapUtilities.computeFewestTurns(graph,a,a)==0)
    assert(MapUtilities.computeFewestTurns(graph,a,b)==1)
    assert(MapUtilities.computeFewestTurns(graph,a,f)==2)
    assert(MapUtilities.computeFewestTurns(graph,e,f)==3)
    assert(MapUtilities.computeFewestTurns(graph,a,i)== -1)
  }

  // ^^^
  behavior of "computeFewestTurnsList"
  it should "..." in {
    val map = mutable.Map[String,mutable.Set[String]]("1"->mutable.Set("A","B","C","D"), "2"->mutable.Set("A","E"), "3"->mutable.Set("B","F"), "4"->mutable.Set("C","H"), "5"->mutable.Set("D","G"), "6"->mutable.Set("I"), "7"->mutable.Set("J","K"))
    val idSet = mutable.Set[String]("1","2","3","4","5")
    val graph = MapUtilities.buildIntersectionGraph(idSet,map)
    val a = new TaxParcel
    a.parcelInfo("STREET") = "a"
    val b = new TaxParcel
    b.parcelInfo("STREET") = "b"
    val e = new TaxParcel
    e.parcelInfo("STREET") = "e"
    val f = new TaxParcel
    f.parcelInfo("STREET") = "f"
    val i = new TaxParcel
    i.parcelInfo("STREET") = "i"
    assert(MapUtilities.computeFewestTurnsList(graph,a,a)==List("A"))
    assert(MapUtilities.computeFewestTurnsList(graph,a,e)==List("A","E"))
    assert(MapUtilities.computeFewestTurnsList(graph,e,f)==List("E","A","B","F"))
    assert(MapUtilities.computeFewestTurnsList(graph,a,i)==List())
  }
}

