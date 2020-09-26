package sample.Model;

public class WeightedEdge extends Edge{
    private int weight;
    public WeightedEdge(Vertex u, Vertex v, boolean directed, int weight){
        super(u,v,directed);
        this.weight = weight;
    }
}
