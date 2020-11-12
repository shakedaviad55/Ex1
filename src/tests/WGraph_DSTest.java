package tests;

import ex1.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    private WGraph_DS g, g1;

    @BeforeEach
    void setUp() {
        g = new WGraph_DS();
        g1 = new WGraph_DS();
        for (int i = 0; i <= 10; i++) {
            g.addNode(i);
            g1.addNode(i);
        }
        for(int i=1;i<10;i++){
            if(i<5){
                g.connect(0,i,i*2);
            }
            else{
                g.connect(5,i,i*3);
            }
        }
        g1.connect(0,1,10);
        g1.connect(0,2,20);
        g1.connect(0,3,30);
        g1.connect(1,2,21);
        g1.connect(1,4,41);
        g1.connect(2,5,25);
        g1.connect(2,3,23);
        g1.connect(4,5,45);
        g1.connect(5,6,56);
        g1.connect(7,8,78);
        g1.connect(8,9,89);
        g1.connect(3,6,36);
        g1.connect(4,7,47);
        g1.connect(5,8,58);
        g1.connect(6,9,69);
        g1.connect(7,10,710);
        g1.connect(8,10,810);
        g1.connect(9,10,910);

    }

    @Test
    void testEquals() {
        weighted_graph gg1=new WGraph_DS(g.toString());
        weighted_graph gg2=g;

        assertAll("Simple check if the graphs are the same",
                ()->assertEquals(gg1,g),
                ()->assertEquals(gg2,g),
                ()->assertEquals(gg1,g),
                ()->assertNotEquals(gg1,g1),
                ()->assertNotEquals(gg2,g1)
        );
        gg1.removeEdge(0,1);
        assertNotEquals(gg1,g);
        gg2.removeEdge(0,1);
        assertEquals(gg1,g);
    }

    @Test
    void testHashCode() {

    }

    @Test
    void getNode() {
    }

    @Test
    void hasEdge() {
    }

    @Test
    void getEdge() {
    }

    @Test
    void addNode() {
    }

    @Test
    void connect() {
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