package ex1;

import java.util.*;


public class WGraph_DS implements weighted_graph {

    private int MC = 1;
    private int EDGES = 0;
    private HashMap<Integer, node_info> V;
    private HashMap<Integer, HashMap<Integer, Double>> E;

    public WGraph_DS() {
        V = new HashMap<Integer, node_info>();
        E = new HashMap<Integer, HashMap<Integer, Double>>();
    }


    public WGraph_DS(String s) {
        this();
        String[] arr = simplify(s);
        if (arr[0].length() > 4) {
            setV(arr[0]);
            if (arr[1].length() > 2)
                setE(arr[1]);
        }
    }

    //    public WGraph_DS(WGraph_DS g){
//        this();
//        g.getV().forEach(node->{
//          addNode(node.getKey());
//        });
//
//        g.getV().forEach(node->{
//           if(g.getE(node.getKey())!=null){
//               g.getE(node.getKey()).forEach(edge->{
//                   connect(node.getKey(),edge,g.getEdge(node.getKey(),edge));
//               });
//           }
//        });
//    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        String s1 = this.toString(), s2 = o.toString();
        return s2.contains(s1) && s1.contains(s2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(EDGES, V, E);
    }

    @Override
    public node_info getNode(int key) {
        if (V.get(key) != null) {
            return V.get(key);
        }
        return null;
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if (E.containsKey(node1)) {
            return E.get(node1).containsKey(node2);
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            if (E.get(node1) != null)
                return E.get(node1).get(node2).doubleValue();
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if (!V.containsKey(key)) {
            V.put(key, new NodeInfo(key));
            MC++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (w >= 0) {
            if (V.containsKey(node1) && V.containsKey(node2)) {
                if (!hasEdge(node1, node2)) {
                    connect(getNode(node1), getNode(node2), w);
                    connect(getNode(node2), getNode(node1), w);
                    MC++;
                    EDGES++;
                }
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return this.V.values();
    }

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

    public Set<Integer> getE(int node_id) {
        if (E.get(node_id) != null) {
            return E.get(node_id).keySet();
        }
        return null;
    }

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

    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            E.get(node1).remove(node2);
            E.get(node2).remove(node1);
            EDGES--;
        }
    }

    @Override
    public int nodeSize() {
        return V.size();
    }

    @Override
    public int edgeSize() {
        return EDGES;
    }

    @Override
    public int getMC() {
        return MC;
    }
    public void setPrev(int key,node_info n){
        NodeInfo t=(NodeInfo)getNode(key);
        t.setPrev(n);
    }
    public node_info getPrev(int key){
        NodeInfo t=(NodeInfo)getNode(key);
        return t.getPrev();
    }
    public String toString() {
        String v = "V:" + V.keySet();
        StringBuilder e = new StringBuilder();
        for (Integer n : E.keySet()) {
            Iterator<Integer> itr = E.get(n).keySet().iterator();
            while (itr.hasNext()) {
                int key = itr.next();
                if (!e.toString().contains("{" + key + "," + n + "|" + getEdge(n, key) + "}"))
                    e.append("{" + n + "," + key + "|" + getEdge(n, key) + "}");
            }
        }

        return v + "\n" + "E:" + e;
    }

    ///////////////// Private Class /////////////////
    private class NodeInfo implements node_info ,Comparable<node_info>{
        private int key;
        private double tag;
        private String info;
        private int counter = 0;
        private node_info prev;


        public NodeInfo() {
            this.tag = 0;
            this.info = "";
            this.key = counter++;
        }

        public NodeInfo(int key, int tag, String info) {
            this.key = key;
            this.info = info;
            this.tag = tag;
        }

        public NodeInfo(int key) {
            this(key, 0, "");
        }


        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        public String toString() {
            return "{" + this.key + "," + this.tag + "," + this.info + "}";
        }

        @Override
        public int compareTo(node_info o) {
            Double c=getTag();
            return c.compareTo(o.getTag());
        }
        public node_info getPrev(){return prev;}

        public void setPrev(node_info prev) {this.prev = prev;}
    }

    ////////// PRIVATE METHODS //////////
    private void connect(node_info node1, node_info node2, double w) {
        if (E.containsKey(node1.getKey())) {
            E.get(node1.getKey()).put(node2.getKey(), w);
        } else {
            HashMap<Integer, Double> e = new HashMap<Integer, Double>();
            e.put(node2.getKey(), w);
            E.put(node1.getKey(), e);
        }
    }

    private String[] simplify(String s) {
        s = s.replace(" ", "");
        return s.split("\n");
    }

    private void setV(String s) {
        s = s.substring(3, s.length() - 1);
        String[] arr = s.split(",");
        for (String i : arr) {
            addNode(parseInt(i));
        }
    }

    private int parseInt(String s) {
        int ans;
        try {
            ans = Integer.parseInt(s);
        } catch (Exception e) {
            throw new RuntimeException("invalid value: please insert a valid number" + s);
        }
        return ans;
    }

    private void setE(String s) {
        s = s.substring(2);
        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()) {
            simplifyEdge(st.nextToken("{}"));
        }
    }

    private void simplifyEdge(String s) {
        StringTokenizer st = new StringTokenizer(s);
        int node1 = parseInt(st.nextToken(",|"));
        int node2 = parseInt(st.nextToken());
        double w = parseDouble(st.nextToken());
        connect(node1, node2, w);

    }

    private double parseDouble(String s) {
        double ans;
        try {
            ans = Double.parseDouble(s);
        } catch (Exception e) {
            throw new RuntimeException("invalid value: please insert a valid number" + s);
        }
        return ans;
    }

}
