package tests;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import ex1.*;

import java.util.Date;

class MassiveGraphTest {
    private weighted_graph_algorithms massive=new WGraph_Algo();
    @BeforeEach
     void setUp(){
        weighted_graph m=new WGraph_DS();
        for(int i=0;i<1000000;i++)m.addNode(i);
        for (int i=1;i<=1000000;i++)m.connect(i-1,i,i);
        massive.init(m);
    }
    @Test
    void copy() {
        weighted_graph copy=massive.copy();
        assertEquals(massive,copy);
        copy.removeNode(copy.nodeSize()/2);
        assertNotEquals(massive,copy);
    }

    @Test
    void isConnected() {
        assertTrue(massive.isConnected());
       for(int i=1;i<6;i++)
           massive.getGraph().removeEdge(0,i);
       assertFalse(massive.isConnected());
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
}