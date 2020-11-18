package ex1;

import java.io.*;
import java.nio.file.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

public class WGraph_Algo implements weighted_graph_algorithms{
    private static final String NOT_VISITED = "white", VISITED = "green", FINISH = "black";
    private weighted_graph graph;

    public WGraph_Algo(){
        graph=new WGraph_DS();
    }
    public WGraph_Algo(weighted_graph g){
        init(g);
    }
    @Override
    public void init(weighted_graph g) { graph=g; }

    @Override
    public weighted_graph getGraph() { return graph; }

    @Override
    public weighted_graph copy() {
        weighted_graph copyGraph=new WGraph_DS();
        for(node_info node:graph.getV()){
            copyGraph.addNode(node.getKey());
        }
        WGraph_DS temp= (WGraph_DS) graph;
        for(node_info node:graph.getV()){
            Set<Integer> ni=temp.getE(node.getKey()) ;
            if(ni!=null){
                for (Integer edge: ni){
                    double w=graph.getEdge(node.getKey(),edge);
                    copyGraph.connect(node.getKey(),edge,w);
                }
            }
        }
        return copyGraph;
    }

    @Override
    public boolean isConnected() {
        if(graph==null) return false;
        if(graph.getV().isEmpty()||graph.nodeSize()==1)return true;

        graph.getV().forEach(node->node.setInfo(NOT_VISITED));
        int firstKey= graph.getV().iterator().next().getKey();
        isConnected(firstKey);

        for (node_info node:graph.getV()){
            if(!node.getInfo().equals(FINISH))
                return false;
        }
        return true;


    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(graph.getNode(src)==null||graph.getNode(dest)==null)
            throw new RuntimeException("Invalid value:node can't be null src="+src+",dest="+dest);
        if(src==dest)return 0;
        for(node_info node:graph.getV()){
            node.setTag(Double.MAX_VALUE);
            node.setInfo(NOT_VISITED);
        }
        graph.getNode(src).setTag(0);
        WGraph_DS t=(WGraph_DS)graph;
        t.setPrev(src,null);
        return shortestPathDist(src,dest,new PriorityBlockingQueue<node_info>(graph.getV()));
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(shortestPathDist(src,dest)==-1)return null;

        return shortestPath(src,dest,new LinkedList<node_info>());
    }

    @Override
    public boolean save(String file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.print(graph);
            pw.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {

        try{
           String content=new String(Files.readAllBytes(Paths.get(file)));

           graph=new WGraph_DS(content);

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public String toString(){
        return graph.toString();
    }
    public boolean equals(Object obj){return graph.equals(obj);}

    ////////// PRIVATE METHODS //////////
    private void isConnected(int key){
        LinkedList<node_info> list=new LinkedList<node_info>();
        graph.getNode(key).setInfo(VISITED);
        list.add(graph.getNode(key));
        while (!list.isEmpty()){
            node_info currNode= list.getFirst();
            list.removeFirst();
            Iterator<node_info> itr=graph.getV(currNode.getKey()).iterator();
            while(itr.hasNext()){
                node_info neighbor=itr.next();
                if(neighbor.getInfo().equals(NOT_VISITED)){
                    neighbor.setInfo(VISITED);
                    list.add(neighbor);

                }
            }
            currNode.setInfo(FINISH);
        }
    }
    private double shortestPathDist(int src,int dest,PriorityBlockingQueue<node_info> queue){

        while (!queue.isEmpty()){
            node_info currNode=queue.remove();

            if(currNode.getKey()==dest||currNode.getTag()==Double.MAX_VALUE){
                if(currNode.getTag()==Double.MAX_VALUE)return -1;
                return currNode.getTag();
            }
            for(node_info neighbor: graph.getV(currNode.getKey())){
                double weight=currNode.getTag()+graph.getEdge(currNode.getKey(),neighbor.getKey());
                if(neighbor.getInfo().equals(NOT_VISITED)&&weight<neighbor.getTag()){
                    neighbor.setTag(weight);
                    queue.remove(neighbor);
                    queue.add(neighbor);

                    WGraph_DS t=(WGraph_DS)graph;
                    t.setPrev(neighbor.getKey(),currNode);
                }
            }
            currNode.setInfo(VISITED);
        }
        return -1;
    }
    private  List<node_info> shortestPath(int src, int dest,LinkedList<node_info> path){

        if(src==dest){
            path.add(graph.getNode(src));
            return path;
        }
        WGraph_DS temp=(WGraph_DS)graph;
        int key=dest;
        path.addFirst(temp.getNode(key));
        while(temp.getPrev(key)!=null){
            node_info n=temp.getPrev(key);
            if(n!=null){
                key=n.getKey();
                path.addFirst(n);
            }
        }
        return path;
    }

}
