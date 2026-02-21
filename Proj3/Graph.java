//Author: Peter Bergen
import java.util.ArrayList;

public class Graph {

    private MyHashMap<String, Node> nodes;
    private ArrayList<Edge> edges;

    private double minLat = Double.POSITIVE_INFINITY;
    private double maxLat = Double.NEGATIVE_INFINITY;
    private double minLon = Double.POSITIVE_INFINITY;
    private double maxLon = Double.NEGATIVE_INFINITY;

    public Graph() {
        nodes = new MyHashMap<String, Node>();
        edges = new ArrayList<Edge>();
    }

    public void addNode(String id, double lat, double lon) {
        Node existing = nodes.get(id);
        if (existing != null) {
            return;
        }
        Node n = new Node(id, lat, lon);
        nodes.put(id, n);

        if (lat < minLat) minLat = lat;
        if (lat > maxLat) maxLat = lat;
        if (lon < minLon) minLon = lon;
        if (lon > maxLon) maxLon = lon;
    }

    public void addUndirectedEdge(String roadId, String fromId, String toId) {
        Node from = nodes.get(fromId);
        Node to = nodes.get(toId);
        if (from == null || to == null) {
            return;
        }
        double dist = distanceMiles(from.getLat(), from.getLon(), to.getLat(), to.getLon());
        Edge e1 = new Edge(roadId, from, to, dist);
        Edge e2 = new Edge(roadId, to, from, dist);
        from.addEdge(e1);
        to.addEdge(e2);
        edges.add(e1);  
    }

    public Node getNode(String id) {
        return nodes.get(id);
    }

    public Iterable<Node> getNodes() {
        return nodes.values();
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public double getMinLat() { return minLat; }
    public double getMaxLat() { return maxLat; }
    public double getMinLon() { return minLon; }
    public double getMaxLon() { return maxLon; }

    public ArrayList<Node> shortestPath(Node start, Node end) {
        for (Node n : getNodes()) {
            n.setDist(Double.POSITIVE_INFINITY);
            n.setPrev(null);
            n.setVisited(false);
        }

        start.setDist(0.0);
        MinHeap<Node> pq = new MinHeap<Node>();
        pq.insert(start);

        while (!pq.isEmpty()) {
            Node u = pq.extractMin();
            if (u.isVisited()) {
                continue;
            }
            u.setVisited(true);
            if (u == end) {
                break;
            }
            for (Edge e : u.getEdges()) {
                Node v = e.getTo();
                if (v.isVisited()) continue;
                double newDist = u.getDist() + e.getWeight();
                if (newDist < v.getDist()) {
                    v.setDist(newDist);
                    v.setPrev(u);
                    pq.insert(v); 
                }
            }
        }

        if (end.getPrev() == null && end != start) {
            return null;
        }

        ArrayList<Node> path = new ArrayList<Node>();
        Node cur = end;
        while (cur != null) {
            path.add(0, cur); 
            cur = cur.getPrev();
        }
        return path;
    }

    private static final double EARTH_RADIUS_MILES = 3958.8;

    private static double distanceMiles(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double rLat1 = Math.toRadians(lat1);
        double rLat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(rLat1) * Math.cos(rLat2) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_MILES * c;
    }
}
