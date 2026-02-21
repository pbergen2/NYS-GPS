//Author: Peter Bergen
import java.util.ArrayList;

public class Node implements Comparable<Node> {

    private String id;
    private double lat;
    private double lon;
    private ArrayList<Edge> edges;

    private double dist;
    private Node prev;
    private boolean visited;

    public Node(String id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.edges = new ArrayList<Edge>();
        this.dist = Double.POSITIVE_INFINITY;
        this.prev = null;
        this.visited = false;
    }

    public String getId() { return id; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public double getDist() { return dist; }
    public void setDist(double d) { dist = d; }

    public Node getPrev() { return prev; }
    public void setPrev(Node p) { prev = p; }

    public boolean isVisited() { return visited; }
    public void setVisited(boolean v) { visited = v; }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.dist, other.dist);
    }
}
