package tests;

import ex1.*;
import org.junit.jupiter.api.*;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    private WGraph_DS g, g1;
    private double EPS=0.000001;

    @BeforeEach
    void setUp() {
        g = new WGraph_DS();
        g1 = new WGraph_DS();
        for (int i = 0; i <= 10; i++) {
            g.addNode(i);
            g1.addNode(i);
        }
        for (int i = 1; i < 10; i++) {
            if (i < 5) {
                g.connect(0, i, i * 2);
            } else {
                g.connect(5, i, i * 3);
            }
        }
        g1.connect(0, 1, 10);
        g1.connect(0, 2, 20);
        g1.connect(0, 3, 30);
        g1.connect(1, 2, 21);
        g1.connect(1, 4, 41);
        g1.connect(2, 5, 25);
        g1.connect(2, 3, 23);
        g1.connect(4, 5, 45);
        g1.connect(5, 6, 56);
        g1.connect(7, 8, 78);
        g1.connect(8, 9, 89);
        g1.connect(3, 6, 36);
        g1.connect(4, 7, 47);
        g1.connect(5, 8, 58);
        g1.connect(6, 9, 69);
        g1.connect(7, 10, 710);
        g1.connect(8, 10, 810);
        g1.connect(9, 10, 910);

    }

    @Test
    void testEquals() {


        assertAll("Simple check if the graphs are the same",
                () -> {
                    weighted_graph gg1 = new WGraph_DS(g.toString());
                    assertEquals(gg1, g);
                    g.removeNode(0);
                    assertNotEquals(gg1, g);

                },
                () -> {
                    weighted_graph gg1 = new WGraph_DS();
                    assertEquals(gg1, new WGraph_DS());
                },
                () -> {
                    weighted_graph gg1 = new WGraph_DS();
                    weighted_graph gg2 = new WGraph_DS();
                    gg1.addNode(0);
                    gg2.addNode(0);
                    assertEquals(gg1, gg2);

                }
        );

    }

    @Test
    void testHashCode() {

    }

    @Test
    void getNode() {
        assertAll(
                () -> assertNull(g1.getNode(11)),
                () -> assertEquals(g.getNode(0), g1.getNode(0))
        );
    }

    @Test
    void hasEdge() {
        for (int i = 1; i < 3; i++) {
            assertTrue(g.hasEdge(0, i));
        }
        for (int i = 0; i < 10; i++) {
            assertFalse(g.hasEdge(i, 10));
        }
        for (int i = 1; i < 4; i++) {
            assertTrue(g1.hasEdge(0, i));
        }
    }

    @Test
    void getEdge() {
        assertAll(
                ()->assertEquals(g1.getEdge(2,3),23,EPS),
                ()->assertEquals(g1.getEdge(6,3),36,EPS),
                ()->assertEquals(g1.getEdge(4,7),47,EPS),
                ()->assertEquals(g1.getEdge(0,3),30,EPS),
                ()->assertEquals(g1.getEdge(2,3),23,EPS),
                ()->assertEquals(g1.getEdge(2,1),21,EPS),
                ()->assertEquals(g1.getEdge(0,10),-1,EPS),
                ()->assertEquals(g1.getEdge(1,5),-1,EPS),
                ()->assertEquals(g1.getEdge(8,3),-1,EPS)
        );
    }

    @Test
    void addNode() {
       weighted_graph gg=new WGraph_DS();
       for(int i=0;i<5;i++)gg.addNode(10);
       assertEquals(gg.nodeSize(),1);
       assertEquals(gg.getNode(10),g.getNode(10));
    }

    @Test
    void connect() {
        assertThrows(RuntimeException.class,()->g.connect(0,1,-1));
        int edgeSize=g.edgeSize();
        for(int i=0;i<5;i++)g.connect(0,1,10);
        assertEquals(edgeSize,g.edgeSize());
        g.connect(0,1,11);
        assertEquals(g.getEdge(0,1),11);
    }

    @Test
    void getV() {

    }

    @Test
    void testGetV() {
    }

    @Test
    void getE() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }

    @Test
    void setPrev() {
    }

    @Test
    void getPrev() {
    }

    @Test
    void testToString() {
    }
}