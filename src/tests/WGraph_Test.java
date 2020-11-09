package tests;

import ex1.WGraph_DS;

import java.util.HashMap;

public class WGraph_Test {
    public static void main(String[] args) {
        WGraph_DS g=new WGraph_DS();
        WGraph_DS g1=new WGraph_DS();
        WGraph_DS g2=new WGraph_DS();
        WGraph_DS g3=new WGraph_DS();
        WGraph_DS g4=new WGraph_DS();
        System.out.println(g3.equals(g4));
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,2);
        g.connect(0,3,5);
        g.connect(0,2,3);
        g.connect(2,0,4);
        g.connect(2,3,4);

        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.connect(0,1,2);
        g1.connect(0,3,5);
        g1.connect(0,2,3);
        g1.connect(2,0,4);
        g1.connect(2,3,4);

        g2.addNode(0);
        g2.addNode(1);
        g2.addNode(2);
        g2.addNode(3);
        g2.connect(0,1,2);
        g2.connect(0,3,2);
        g2.connect(0,2,2);
        g2.connect(2,0,2);
        g2.connect(2,3,2);
//        System.out.println(g.toString());
//        System.out.println(g.hasEdge(0,1));
//        System.out.println(g.hasEdge(0,2));
//        System.out.println(g.getEdge(0,1));
//
//        System.out.println(g.getEdge(0,2));
//        System.out.println("nodes:"+g.nodeSize());
//        System.out.println("edges:"+g.edgeSize());
//        System.out.println("MC:"+g.getMC());
//
//        System.out.println("V from 0"+g.getV(0));
//        System.out.println("V from 1"+g.getV(1));
//        System.out.println("V from 2"+g.getV(2));
//
//        g.removeEdge(0,2);
//        System.out.println("after remove edge 1 from 0");
//        System.out.println("nodes:"+g.nodeSize());
//        System.out.println("edges:"+g.edgeSize());
//        System.out.println("MC:"+g.getMC());
//
//
//        System.out.println("V from 0"+g.getV(0));
//        System.out.println("V from 1"+g.getV(1));
//        System.out.println("V from 2"+g.getV(2));
 //       g.removeNode(0);
//
//        System.out.println("after remove node 0");
//        System.out.println("V from 0"+g.getV(0));
//        System.out.println("V from 1"+g.getV(1));
//        System.out.println("V from 2"+g.getV(2));
//
//        System.out.println("nodes:"+g.nodeSize());
//        System.out.println("edges:"+g.edgeSize());
//        System.out.println("MC:"+g.getMC());
//        System.out.println(g.toString());
        System.out.println("g"+g.toString());
        System.out.println("g1"+g1.toString());
        System.out.println("g2"+g2.toString());
        System.out.println("equals!!");
        System.out.println("g1==g2"+g1.equals(g2));
        System.out.println("g==g2"+g.equals(g2));
        System.out.println("g==g1"+g.equals(g1));
    }
}