package tests;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import ex1.*;

import java.util.Arrays;
import java.util.Date;

class MassiveGraphTest {
    private weighted_graph_algorithms massive=new WGraph_Algo();
    @BeforeEach
     void setUp(){
        weighted_graph m=new WGraph_DS();
        for(int i=0;i<200000;i++){
            m.addNode(i);
        }

        for (int i=1;i<=200000;i++){
//            m.connect(i-5,i-4,i-4);
//            m.connect(i-5,i-3,i-3);
//            m.connect(i-5,i-2,i-2);
//            m.connect(i-5,i-1,i-1);
//            m.connect(i-5,i,i);
            m.connect(i-1,i,i);
        }
        massive.init(m);
    }


    @Test
    void copy() {
        long start= new Date().getTime();
        weighted_graph copy=massive.copy();
        assertEquals(massive,copy);
        copy.removeNode(copy.nodeSize()/2);
        assertNotEquals(massive,copy);
        long end=new Date().getTime();
        double ans=(end-start)/1000;
        assertTrue(ans<8);
    }

    @Test
    void isConnected() {
        long start= new Date().getTime();
        assertTrue(massive.isConnected());
       for(int i=1;i<6;i++)
           massive.getGraph().removeEdge(0,i);

       assertFalse(massive.isConnected());
        long end=new Date().getTime();
        double ans=(end-start)/1000;
        assertTrue(ans<3);
    }

    @Test
    void shortestPathDist() {
        double ans =0;
        for(int i=1;i<=10000;i++){ans+=i;}
        assertEquals(ans,massive.shortestPathDist(0,10000));

    }

    @Test
    void shortestPath() {
        assertEquals(10001,massive.shortestPath(0,10000).size());
    }

    @Test
    void saveAndLoad() {
        massive.save("test.txt");
        weighted_graph_algorithms text=new WGraph_Algo();
        text.load("test.txt");
        assertEquals(text,massive);
    }
    @Test
    void testToString() {
    }

}