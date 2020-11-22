package ex1.src;

import java.io.*;
import java.nio.file.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * This class represents the algorithms of weighted_graph
 * @author Shaked Aviad
 */
public class WGraph_Algo implements weighted_graph_algorithms{
    private static final String NOT_VISITED = "white", VISITED = "green", FINISH = "black";
    private weighted_graph graph;

    /**
     * Default constructor
     * @return new WGraph_Algo
     */
    public WGraph_Algo(){ graph=new WGraph_DS(); }

    /**
     * Constructor
     * Shallow copy operation
     * @param g
     */
    public WGraph_Algo(weighted_graph g){init(g);}
    /**
     * Initialization of an algorithm graph by a given graph g
     * @param g
     */
    @Override
    public void init(weighted_graph g) { graph=g; }

    /**
     * Returns the base graph of the class
     * @return weighted_graph
     */
    @Override
    public weighted_graph getGraph() { return graph; }

    /**
     * Deep copying
     * Copies every vertex and every edge which exists in the copied graph
     * @return weighted_graph
     */
    @Override
    public weighted_graph copy() {
        weighted_graph copyGraph=new WGraph_DS();
        for(node_info node: graph.getV()){
            copyGraph.addNode(node.getKey());
            for (node_info ni:graph.getV(node.getKey()))
                copyGraph.connect(
                            node.getKey(),
                            ni.getKey(),
                            graph.getEdge(node.getKey(), ni.getKey())
                );
        }
        return copyGraph;
    }

    /**
     * Checks if it is possible to reach from any vertex to any other
     * Minimum conditions:
     * If the graph is null return false
     * If the graph is empty, or with only one vertex return true
     * First initializes all vertices with NOT_VISITED information
     * Once the basic graph has returned from the auxiliary function
     * and has a vertex which hasn't reached it, return false else true
     * An explanation of the auxiliary function is explained below
     * @return boolean
     */
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

    /**
     * Checking the shortest path from vertex src to vertex dest
     * If src or dest is null, throws RuntimeException
     * if src = dest return 0
     * First initialize all vertices with NOT_VISITED and MAX_VALUE
     * And updates the previous vertex to be null
     * An explanation of the auxiliary function is below
     * @param src - start node
     * @param dest - end (target) node
     * @return double
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(graph.getNode(src)==null||graph.getNode(dest)==null)
            throw new RuntimeException("Invalid value:node can't be null ex1.ex1.src="+src+",dest="+dest);
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

    /**
     * Returns all vertices form the shortest path from vertex src to dest
     * If there is no such path, returns null
     * If vertex src or dest is null, throws RuntimeException
     * An explanation of the auxiliary function is below
     * @param src - start node
     * @param dest - end (target) node
     * @return List<node_info>
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(shortestPathDist(src,dest)==-1)return null;
        return shortestPath(src,dest,new LinkedList<node_info>());
    }

    /**
     * Saves the basic graph to the file
     * Uses PrintWriter Because of its convenient display in the text file
     * @param file - the file name (may include a relative path).
     * @return boolean
     */
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

    /**
     * Loading text file to graph
     * Converts it to String
     * and sends it to a constructor which receives String
     * If one of the values obtained is invalid, throws RuntimeException
     * @param file - file name
     * @return boolean
     */
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

    /**
     * Converts the base graph to a string
     * @return String
     */
    public String toString(){
        return graph.toString();
    }

    /**
     * Compares two graphs using equals function of WGraph_DS
     * @param obj
     * @return boolean
     */
    public boolean equals(Object obj){return graph.equals(obj);}

    ////////// PRIVATE METHODS //////////

    /**
     * Auxiliary function,
     * First, there's the first vertex in the list
     * Initialize the first vertex from the list
     * and run through his neighbors.
     * If the vertices' info is NOT_VISITED
     * Adds it to the list and changes its info to VISITED
     * Changes the info of the first vertex to FINISH
     * Deletes the first vertex from the list,
     * And goes on until the list is completely empty
     * @param key
     */
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

    /**
     * Auxiliary function,
     * First, there's the first vertex in the queue.
     * Goes through all the vertices until you reach the vertex dest
     * Initializes the vertex with the shortest path currNode
     * And delete it from the queue
     * 1) if currNode is dest, Return it
     * 2) If its value is MAX_VALUE, returns -1
     * else pass through all its neighbors, if a shorter path to the neighbor is found
     * and not yet VISITED, updates its value to be the shorter path
     * And his prev to be currNode
     * Changes the information of currNode to VISITED
     * Keeps running until the queue is empty, 1 or 2
     * @param src
     * @param dest
     * @param queue
     * @return double
     */
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

    /**
     * Auxiliary function,
     * Using shortestPathDist, I obtained the shortest path from src to dest
     * runs from dest to src via the previous vertices
     * Until it gets to null, which signifies we've reached the vertex src
     * @param src
     * @param dest
     * @param path
     * @return  List<node_info>
     */
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

