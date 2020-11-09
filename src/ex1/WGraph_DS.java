package ex1;

import java.util.*;

public class WGraph_DS implements weighted_graph{
    //test git
    private int MC=1;
    private int EDGES=0;
    private HashMap<Integer,node_info>V;
    private HashMap<Integer,HashMap<Integer,Double>>E;

    public WGraph_DS(){
        V=new HashMap<Integer,node_info>();
        E=new HashMap<Integer,HashMap<Integer,Double>>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        String s1=this.toString(),s2= o.toString();
        return s2.contains(s1)&& s1.contains(s2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(EDGES, V, E);
    }

    @Override
    public node_info getNode(int key) {
        if(V.get(key)!=null){
            return V.get(key);
        }
        return null;
    }

    @Override
    public boolean hasEdge(int node1, int node2){
        if(E.containsKey(node1)){
            return E.get(node1).containsKey(node2);
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2)){
           if(E.get(node1)!=null)
                return E.get(node1).get(node2).doubleValue();
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if(!V.containsKey(key)){
            V.put(key,new NodeInfo(key));
            MC++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if(w>=0){
            if(V.containsKey(node1)&&V.containsKey(node2)){
                if(!hasEdge(node1,node2)) {
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
      List<node_info> list=new ArrayList<node_info>();
      if(E.containsKey(node_id)){
          for(Integer node:E.get(node_id).keySet()) {
              list.add(getNode(node));
          }
      }
        return list;
    }

    @Override
    public node_info removeNode(int key) {
       if(E.containsKey(key)) {
         Iterator<node_info>itr=getV(key).iterator();
         while(itr.hasNext()){
             removeEdge(key,itr.next().getKey());
         }
        MC++;
       }
        return V.remove(key);
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1,node2)){
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
    public String toString(){
        String v="V:"+V.keySet();
        StringBuilder e = new StringBuilder();
        for (Integer n:E.keySet()){
           Iterator<Integer>itr =E.get(n).keySet().iterator();
           while (itr.hasNext()){
               int key=itr.next();
               if(!e.toString().contains("{"+key+","+n+"|"+getEdge(n,key)+"}"))
                e.append("{"+n+","+key+"|"+getEdge(n,key)+"}");
           }
        }

        return v+"\n"+"E:"+e;
    }
  ///////////////// Private Class /////////////////
    private  class NodeInfo implements node_info{
        private int key;
        private double tag;
        private String info;
        private  int counter=0;


        public NodeInfo(){
            this.tag=0;
            this.info="";
            this.key=counter++;
        }
        public NodeInfo(int key,int tag,String info){
            this.key=key;
            this.info=info;
            this.tag=tag;
        }
        public NodeInfo(int key){
            this(key,0,"");
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
            this.info=s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag=t;
        }
        public String toString(){
            return "{"+this.key+","+this.tag+","+this.info+"}";
        }
    }
    ////////// PRIVATE METHODS //////////
    private void connect(node_info node1,node_info node2,double w){
        if(E.containsKey(node1.getKey())){
            E.get(node1.getKey()).put(node2.getKey(),w);
        }
        else{
            HashMap<Integer,Double> e=new HashMap<Integer, Double>();
            e.put(node2.getKey(),w);
            E.put(node1.getKey(),e);
        }

    }
}
