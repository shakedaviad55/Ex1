package ex1;

import java.util.List;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;

    public WGraph_Algo(){
        graph=new WGraph_DS();
    }
    @Override
    public void init(weighted_graph g) {
        graph=g;
    }

    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    @Override
    public weighted_graph copy() {
        weighted_graph copyGraph=new WGraph_DS();
        for(node_info node:graph.getV()){
            copyGraph.addNode(node.getKey());
        }
        WGraph_DS temp= (WGraph_DS) graph;
        for(node_info node:graph.getV()){
            if(temp.getE(node.getKey())!=null){
                for (Integer edge: temp.getE(node.getKey())){
                    double w=graph.getEdge(node.getKey(),edge);
                    copyGraph.connect(node.getKey(),edge,w);
                }
            }
        }
        return copyGraph;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
    public String toString(){
        return graph.toString();
    }
}
