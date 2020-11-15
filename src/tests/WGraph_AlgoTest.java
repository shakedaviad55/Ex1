package tests;

import org.junit.jupiter.api.*;
import ex1.*;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
   private weighted_graph_algorithms complex,sun,single,line,empty;
   private double EPS=0.000001;
    @BeforeEach
    void setUp(){
        weighted_graph
                c=new WGraph_DS(),
                s=new WGraph_DS(),
                l=new WGraph_DS(),
                su=new WGraph_DS(),
                e=new WGraph_DS();
        for(int i=0;i<10;i++){
            c.addNode(i);
            su.addNode(i);
            l.addNode(i);
        }
        s.addNode(0);
        for(int i=1;i<10;i++){
            l.connect(i,i-1,i*10);
            if(i<5)su.connect(0,i,i);
            if(i>5)su.connect(5,i,i);
        }
        c=setUpComplex(c);
        s.addNode(0);

                complex=new WGraph_Algo(c);
                sun=new WGraph_Algo(su);
                single=new WGraph_Algo(s);
                line=new WGraph_Algo(l);
                empty=new WGraph_Algo(e);

    }
    @Test
    void init() {
        weighted_graph l2=new WGraph_DS();
        for (int i=0;i<10;i++)l2.addNode(i);
        for (int i=1;i<10;i++)l2.connect(i-1,i,i*10);
        weighted_graph_algorithms line2=new WGraph_Algo();
        line2.init(l2);
        assertEquals(line,line2);
        l2.removeNode(5);
        assertNotEquals(line,line2);


    }

    @Test
    void getGraph() {
        assertEquals(empty.getGraph(),new WGraph_DS());
        assertEquals(empty.getGraph(),new WGraph_Algo());

        assertEquals(complex.getGraph(),complex);

        complex.getGraph().removeNode(2);

        assertEquals(complex.getGraph(),complex);

    }

    @Test
    void copy() {
        weighted_graph c= empty.copy();
        assertEquals(empty,c);

        weighted_graph s=sun.copy();
        assertEquals(s,sun);

        s.addNode(11);

        assertNotEquals(s,sun);

        s.removeNode(11);
        assertEquals(s,sun);


    }

    @Test
    void isConnected() {
        assertTrue(empty.isConnected());
        assertTrue(single.isConnected());
        assertTrue(complex.isConnected());

        assertFalse(sun.isConnected());
        sun.getGraph().connect(1,9,19);
        assertTrue(sun.isConnected());
    }

    @Test
    void shortestPathDist() {

        assertThrows(RuntimeException.class,()->empty.shortestPathDist(1,2));
        assertThrows(RuntimeException.class,()->single.shortestPathDist(0,2));

        assertEquals(6,complex.shortestPathDist(1,2),EPS);
        assertEquals(-1,sun.shortestPathDist(0,5),EPS);
        assertEquals(50+60+70,line.shortestPathDist(4,7),EPS);
        line.getGraph().removeEdge(4,5);
        assertEquals(-1,line.shortestPathDist(4,7),EPS);
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
    private weighted_graph setUpComplex(weighted_graph c){

        c.connect(0,1,7);
        c.connect(0,2,6);
        c.connect(0,3,5);

        c.connect(1,2,100);
        c.connect(1,4,1);

        c.connect(2,5,15);
        c.connect(2,3,1);
        c.connect(3,6,1);

        c.connect(4,7,5);
        c.connect(4,5,1);
        c.connect(5,7,1);
        c.connect(5,8,1);
        c.connect(5,6,5);

        c.connect(6,8,1);
        c.connect(7,9,10);
        c.connect(8,9,15);

        return c;
    }
}