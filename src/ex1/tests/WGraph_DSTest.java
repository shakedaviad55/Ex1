package ex1.tests;

import ex1.src.*;

import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

/**
 * This testing class checks all the functions of WGraph_DS
 * using two graphs : g,g1
 * See Wiki's project
 * @author Shaked Aviad
 */
class WGraph_DSTest {
    private WGraph_DS g, g1;
    private double EPS = 0.000001;

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
                    gg1.addNode(100);
                    assertNotEquals(g, gg1);
                    g.removeNode(0);
                    assertNotEquals(gg1, g);
                    assertNotEquals(new WGraph_DS(), g1);
                    assertNotEquals(g1,new WGraph_DS());
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
                () -> assertEquals(23, g1.getEdge(2, 3), EPS),
                () -> assertEquals(36, g1.getEdge(6, 3), EPS),
                () -> assertEquals(47, g1.getEdge(4, 7), EPS),
                () -> assertEquals(30, g1.getEdge(0, 3), EPS),
                () -> assertEquals(23, g1.getEdge(2, 3), EPS),
                () -> assertEquals(21, g1.getEdge(2, 1), EPS),
                () -> assertEquals(-1, g1.getEdge(0, 10), EPS),
                () -> assertEquals(-1, g1.getEdge(1, 5), EPS),
                () -> assertEquals(-1, g1.getEdge(8, 3), EPS)
        );
    }

    @Test
    void addNode() {
        weighted_graph gg = new WGraph_DS();
        for (int i = 0; i < 5; i++) gg.addNode(10);
        assertEquals(1, gg.nodeSize());
        assertEquals(gg.getNode(10), g.getNode(10));
    }

    @Test
    void connect() {
        assertThrows(RuntimeException.class, () -> g.connect(0, 1, -1));
        int edgeSize = g.edgeSize();
        for (int i = 0; i < 5; i++) g.connect(0, 1, 10);
        assertEquals(edgeSize, g.edgeSize());
        g.connect(0, 1, 11);
        assertEquals(11, g.getEdge(0, 1));
    }

    @Test
    void getV() {
        assertAll("Test for all nodes in a graph",
                () -> {
                    String s = "[{0,0.0,}, {1,0.0,}, {2,0.0,}, {3,0.0,}, {4,0.0,}, {5,0.0,}, {6,0.0,}, {7,0.0,}, {8,0.0,}, {9,0.0,}, {10,0.0,}]";
                    assertTrue(g.getV().toString().contains(s));
                    assertTrue(s.contains(g.getV().toString()));
                    g.removeNode(0);
                    s = "[{1,0.0,}, {2,0.0,}, {3,0.0,}, {4,0.0,}, {5,0.0,}, {6,0.0,}, {7,0.0,}, {8,0.0,}, {9,0.0,}, {10,0.0,}]";
                    assertTrue(g.getV().toString().contains(s));
                    assertTrue(s.contains(g.getV().toString()));
                },
                () -> assertEquals(g.getV().size(), g.nodeSize())
        );
    }

    @Test
    void testGetV() {
        String s = "[{6,0.0,}, {7,0.0,}, {8,0.0,}, {9,0.0,}]";
        assertTrue(g.getV(5).toString().contains(s));
        assertTrue(s.contains(g.getV(5).toString()));
        g.removeEdge(5, 6);
        s = "[{7,0.0,}, {8,0.0,}, {9,0.0,}]";
        assertTrue(g.getV(5).toString().contains(s));
        assertTrue(s.contains(g.getV(5).toString()));
    }

    @Test
    void removeNode() {
        assertAll(
                () -> assertEquals(3, g1.getV(0).size()),
                () -> assertTrue(g1.hasEdge(0, 1)),
                () -> assertTrue(g1.hasEdge(0, 2)),
                () -> assertTrue(g1.hasEdge(0, 3)),
                () -> assertEquals(11, g1.nodeSize()),
                () -> assertEquals(18, g1.edgeSize()),
                () -> {
                    g1.removeNode(0);
                    assertEquals(g1.getV(0).size(), 0);
                    assertFalse(g1.hasEdge(0, 1));
                    assertFalse(g1.hasEdge(0, 2));
                    assertFalse(g1.hasEdge(0, 3));
                    assertEquals(10, g1.nodeSize());
                    assertEquals(15, g1.edgeSize());
                }
        );
    }

    @Test
    void removeEdge() {
        assertAll(
                () -> assertTrue(g.hasEdge(0, 1)),
                () -> assertEquals(8, g.edgeSize()),
                () -> {
                    g.removeEdge(0, 1);
                    assertFalse(g.hasEdge(0, 1));
                    assertEquals(7, g.edgeSize());
                }
        );
    }

    @Test
    void nodeSize() {
        assertEquals(11, g.nodeSize());
        g.removeNode(0);
        assertEquals(10, g.nodeSize());
        g.addNode(0);
        assertEquals(11, g.nodeSize());
    }

    @Test
    void edgeSize() {
        assertAll(
                () -> assertEquals(18, g1.edgeSize()),
                () -> assertTrue(g1.hasEdge(7, 10)),
                () -> {
                    g1.removeEdge(7, 10);
                    assertEquals(17, g1.edgeSize());
                    assertFalse(g1.hasEdge(7, 10));
                },
                ()->{
                    g1.removeNode(7);
                    assertEquals(15, g1.edgeSize());
                    assertFalse(g1.hasEdge(7, 10));
                    assertFalse(g1.hasEdge(7, 8));
                    assertFalse(g1.hasEdge(7, 4));
                },
                ()->{
                    int edge=g1.edgeSize();
                    g1.connect(0,1,0);
                    assertEquals(edge,g1.edgeSize());
                    assertEquals(0,g1.getEdge(0,1));
                }
        );
    }

    @Test
    void getMC() {
        weighted_graph gg=new WGraph_DS();
        assertEquals(1,gg.getMC());
        gg.addNode(0);
        assertEquals(2,gg.getMC());
        gg.addNode(1);
        gg.connect(0,1,10);
        assertEquals(4,gg.getMC());
        gg.removeNode(0);
        assertEquals(6,gg.getMC());
    }

    @Test
    void setAndGetPrev() {
        WGraph_DS gg=new WGraph_DS();
        gg.addNode(0);
        gg.addNode(1);
        gg.setPrev(0,gg.getNode(1));
        assertEquals(gg.getNode(1),gg.getPrev(0));
        gg.setPrev(1,gg.getNode(0));
        assertEquals(gg.getNode(0),gg.getPrev(1));
    }

    @Test
    void testToString() {
        weighted_graph gg=new WGraph_DS();
        gg.addNode(0);
        gg.addNode(1);
        gg.addNode(2);
        gg.connect(0,1,1);
        gg.connect(1,2,1);
        gg.connect(2,0,1);

        String s="V:[0, 1, 2]\n"+"E:[{0,2|1.0}, {1,2|1.0}, {0,1|1.0}]";
        assertTrue(gg.toString().contains(s));
        weighted_graph gg1=new WGraph_DS(gg.toString());
        assertEquals(gg,gg1);
    }
}