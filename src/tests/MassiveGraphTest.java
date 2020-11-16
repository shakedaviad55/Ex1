package tests;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import ex1.*;

import java.util.Date;

class MassiveGraphTest {
    private weighted_graph_algorithms massive=new WGraph_Algo();
    @BeforeEach
     void setUp(){
        weighted_graph m=new WGraph_DS();
        for(int i=0;i<200000;i++){
            m.addNode(i);
        }
        for (int i=5;i<=200000;i++){
            m.connect(i-5,i-4,i-4);
            m.connect(i-5,i-3,i-3);
            m.connect(i-5,i-2,i-2);
            m.connect(i-5,i-1,i-1);
            m.connect(i-5,i,i);
        }
        massive.init(m);
    }



    @Test
    void copy() {
        long s=new Date().getTime();
        weighted_graph c=massive.copy();
        long e=new Date().getTime();
        double ans=(e-s)/10000.0;
        int edge=c.edgeSize();
        int node=c.nodeSize();
        assertEquals(c,massive);
        int dick=0;
    }



    @Test
    void isConnected() {
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }
}