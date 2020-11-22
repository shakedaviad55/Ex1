package ex1.src;


import java.util.*;
/**
 * This class represents a weighted and unintentional graph
 * V represents the nodes of the graph. In a data structure of HashMap<Integer,noe_info>
 * E represents the edges of the graph. In a data structure of HashMap<Integer,HashMap<Integer,Double>>
 * @author Shaked Aviad
 */
public class WGraph_DS implements weighted_graph {
    private int MC = 1;
    private int EDGES = 0;
    private HashMap<Integer, node_info> V;
    private HashMap<Integer, HashMap<Integer, Double>> E;
    /**
     * Default constructor
     * @return a new WGraph_DS
     */
    public WGraph_DS() {
        V = new HashMap<Integer, node_info>();
        E = new HashMap<Integer, HashMap<Integer, Double>>();
    }
    /**
     * A constructor that receives a string and returns a graph based on the string
     * An explanation about the functions setE() & setV() is down below
     * If it returns an invalid value -> throws RunTimeException
     * @param  s
     * @return a new WGraph_DS
     */

    public WGraph_DS(String s) {
        this();
        String[] arr = simplify(s);
        if (arr[0].length() > 4) {
            setV(arr[0]);
            if (arr[1].length() > 4)
                setE(arr[1]);
        }
    }
    /**
     * A comparison function returns true if the graphs are logically identical
     * The similar function is explained below
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        weighted_graph obj = new WGraph_DS();
        if (o instanceof weighted_graph) {
            obj = (weighted_graph) o;
        }
        if (o instanceof weighted_graph_algorithms) {
            o = ((weighted_graph_algorithms) o).getGraph();
            obj = (weighted_graph) o;
        }
        return similar(this, obj);
    }
    @Override
    public int hashCode() {
        return Objects.hash(EDGES, V, E);
    }

    /**
     * Returns the requested node by the unique key, if exists, else returns null
     * @param key - the node_id
     * @return node_info
     */
    @Override
    public node_info getNode(int key) {
        if (V.get(key) != null) {
            return V.get(key);
        }
        return null;
    }

    /**
     * Returns true if there is an edge between the two vertices, else false
     * @param node1
     * @param node2
     * @return boolean
     */

    @Override
    public boolean hasEdge(int node1, int node2) {
        if (E.containsKey(node1)) {
            return E.get(node1).containsKey(node2);
        }
        return false;
    }

    /**
     * Returns the weight between the two vertices if exists, else return -1
     * @param node1
     * @param node2
     * @return Double w
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            if (E.get(node1) != null)
                return E.get(node1).get(node2).doubleValue();
        }
        return -1;
    }

    /**
     * Adds a new node to the graph V if no such node exists
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!V.containsKey(key)) {
            V.put(key, new NodeInfo(key));
            MC++;
        }
    }

    /**
     * Connects an edge between two vertices if there is no edge between them.
     * if the edge do exists updates the new weight
     * And updates E
     * Throws a RuntimeException if w is a negative number
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (w >= 0) {
            if (node1 == node2) return;
            if (V.containsKey(node1) && V.containsKey(node2)) {
                if (!hasEdge(node1, node2)) EDGES++;
                connect(getNode(node1), getNode(node2), w);
                connect(getNode(node2), getNode(node1), w);
                MC++;
            }
        } else throw new RuntimeException("Invalid value:weight can't be a negative number w=" + w);
    }

    /**
     * Returns all vertices of the graph
     * @return Collection<node_info>
     */
    @Override
    public Collection<node_info> getV() {
        return this.V.values();
    }

    /**
     * Returns all neighbors of the specific node
     * @param node_id
     * @return  Collection<node_info>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        List<node_info> list = new ArrayList<node_info>();
        if (E.containsKey(node_id)) {
            for (Integer node : E.get(node_id).keySet()) {
                list.add(getNode(node));
            }
        }
        return list;
    }

    /**
     * Deletes a specific node from the graph and all the edges connected to it
     * @param key
     * @return node_info
     */
    @Override
    public node_info removeNode(int key) {
        if (E.containsKey(key)) {
            Iterator<node_info> itr = getV(key).iterator();
            while (itr.hasNext()) {
                removeEdge(key, itr.next().getKey());
            }
            MC++;
        }
        return V.remove(key);
    }

    /**
     * Deletes a specific edge if exists
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            E.get(node1).remove(node2);
            E.get(node2).remove(node1);
            EDGES--;
            MC++;
        }
    }

    /**
     * Returns the amount of vertices in the graph
     * @return int
     */
    @Override
    public int nodeSize() {
        return V.size();
    }
    /**
     * Returns the amount of edges in the graph
     * @return int
     */
    @Override
    public int edgeSize() {
        return EDGES;
    }

    /**
     * Returns the amount of changes that occurred in the graph
     * @return int
     */
    @Override
    public int getMC() {
        return MC;
    }

    /**
     * For graph algorithm,
     * updates the previous node we got from to the current node
     * @param key
     * @param n
     */
    public void setPrev(int key, node_info n) {
        NodeInfo t = (NodeInfo) getNode(key);
        t.setPrev(n);
    }

    /**
     * For graph algorithm,
     * returns the previous node we got from to the current node
     *
     * @param key
     * @return node_info
     */
    public node_info getPrev(int key) {
        NodeInfo t = (NodeInfo) getNode(key);
        return t.getPrev();
    }

    /**
     * Returns the string of the graph in this particular way: V:[v1,v2,...,vn]\n E:[{vi,vj|w},...]
     * @return String
     */
    public String toString() {
        String v = "V:" + V.keySet();
        HashMap<String, Integer> e = new HashMap<>();
        for (Integer n : E.keySet()) {
            Iterator<Integer> itr = E.get(n).keySet().iterator();
            while (itr.hasNext()) {
                int key = itr.next();
                if (e.get("{" + key + "," + n + "|" + getEdge(n, key) + "}") == null) {
                    e.put("{" + n + "," + key + "|" + getEdge(n, key) + "}", 0);
                }
            }
        }
        return v + "\n" + "E:" +e.keySet();
    }

    ///////////////// Private Class /////////////////

    /**
     * A private class represents the node of the graph
     * @author Shaked Aviad
     */
    private class NodeInfo implements node_info, Comparable<node_info>  {
        private int key;
        private double tag;
        private String info;
        private int counter = 0;
        private node_info prev;

        /**
         * Default constructor
         * @return a new node_info
         */
        public NodeInfo() {
            this.tag = 0;
            this.info = "";
            this.key = counter++;
        }

        /**
         * Constructor
         * @param key
         * @param tag
         * @param info
         * @return new node_info
         */
        public NodeInfo(int key, int tag, String info) {
            this.key = key;
            this.info = info;
            this.tag = tag;
        }
        /**
         * Constructor
         * @param key
         * @return new node_info
         */

        public NodeInfo(int key) {
            this(key, 0, "");
        }

        /**
         * Returns the key of the node
         * @return int
         */
        @Override
        public int getKey() {
            return this.key;
        }

        /**
         * Returns the info of the node
         * @return String
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * Updates the node information
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }
        /**
         * Returns the tag of the node
         * @return double
         */
        @Override
        public double getTag() {
            return this.tag;
        }
        /**
         * Updates the node's tag
         * @param t
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * Returns the node as a string
         * @return String
         */
        public String toString() {
            return "{" + this.key + "," + this.tag + "," + this.info + "}";
        }

        /**
         * For graph algorithm,
         * compares two nodes by their tag
         * @param o
         * @return int
         */
        @Override
        public int compareTo(node_info o) {
            Double c = getTag();
            return c.compareTo(o.getTag());
        }
/////////// Private Method //////////

        /**
         * For graph algorithm,
         * returns the previous node which we got from to this current node
         * @return node_info
         */
        private node_info getPrev() {
            return prev;
        }

        /**
         * For graph algorithm,
         * updates the previous node which we got from to this current node
         * @param prev
         */
        private void setPrev(node_info prev) {
            this.prev = prev;
        }

        /**
         * Compares two nodes by the variables of the node
         * @param obj
         * @return
         */
        public boolean equals(Object obj) {
            node_info o = (NodeInfo) obj;
            return o.getTag() == this.tag &&
                    o.getInfo() == this.info &&
                    o.getKey() == this.getKey();
        }
    }

    ////////// PRIVATE METHODS //////////

    /**
     * Auxiliary function, checks whether the node exists in E
     * if it creates a new neighbor for it
     * and if not adds to graph E
     * @param node1
     * @param node2
     * @param w
     */
    private void connect(node_info node1, node_info node2, double w) {
        if (E.containsKey(node1.getKey())) {
            E.get(node1.getKey()).put(node2.getKey(), w);
        } else {
            HashMap<Integer, Double> e = new HashMap<Integer, Double>();
            e.put(node2.getKey(), w);
            E.put(node1.getKey(), e);
        }
    }

    /**
     * Auxiliary function, returns an array of strings
     * so that the first member has the V
     * and the second member has the E
     * @param s
     * @return String []
     */
    private String[] simplify(String s) {
        s = s.replace(" ", "");
        return s.split("\n");
    }

    /**
     * Auxiliary function, converts from a string to an int
     * and inserts the new vertex into the graph
     * Throws  RuntimeException If a value that cannot be converted to int
     * @param s
     */
    private void setV(String s) {
        s = s.substring(3, s.length() - 1);
        String[] arr = s.split(",");
        for (String i : arr) {
            addNode(parseInt(i));
        }
    }

    /**
     * Auxiliary function, checks whether it can be converted to INT,
     * then converts it, does not throws RuntimeException
     * @param s
     * @return int
     */
    private int parseInt(String s) {
        int ans;
        try {
            ans = Integer.parseInt(s);
        } catch (Exception e) {
            throw new RuntimeException("invalid value: please insert a valid number" + s);
        }
        return ans;
    }

    /**
     * Auxiliary function, gets a string and turns it into edges in graph E.
     * If it fails in converting, throws RuntimeException
     * @param s
     */
    private void setE(String s) {
        s = s.substring(3,s.length()-1);
        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()) {
            simplifyEdge(st.nextToken("{}"));
            if(st.hasMoreTokens())st.nextToken();
        }
    }
    /**
     * Auxiliary function, simplifies any expression of type {v1,v2|w}
     * If it fails in converting, throws RuntimeException
     * @param s
     */
    private void simplifyEdge(String s) {
        StringTokenizer st = new StringTokenizer(s);
        int node1 = parseInt(st.nextToken(",|"));
        int node2 = parseInt(st.nextToken());
        double w = parseDouble(st.nextToken());
        connect(node1, node2, w);

    }
    /**
     * Auxiliary function, checks whether it can be converted to Double,
     * then converts it, does not throws  RuntimeException
     * @param s
     * @return double
     */
    private double parseDouble(String s) {
        double ans;
        try {
            ans = Double.parseDouble(s);
        } catch (Exception e) {
            throw new RuntimeException("invalid value: please insert a valid number" + s);
        }
        return ans;
    }

    /**
     * Auxiliary function,
     * Checks if two graphs are logically identical,
     * aka they contain the same vertices and the same edges
     * @param a
     * @param b
     * @return boolean
     */
    private boolean similar(weighted_graph a, weighted_graph b) {
        if (a.edgeSize() != b.edgeSize() || a.nodeSize() != b.nodeSize()) return false;
        for (node_info node : a.getV()) {
            if (b.getNode(node.getKey()) == null) return false;
            if (!a.getV(node.getKey()).equals(b.getV(node.getKey()))) return false;
        }
        for (node_info node : b.getV()) {
            if (a.getNode(node.getKey()) == null) return false;
            if (!b.getV(node.getKey()).equals(a.getV(node.getKey()))) return false;
        }
        return true;
    }

}
