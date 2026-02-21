//Author: Peter Bergen
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class StreetMap {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java StreetMap map.txt [--show] [--directions start end]");
            return;
        }

        String filename = args[0];
        boolean showMap = false;
        String startId = null;
        String endId = null;

        int i = 1;
        while (i < args.length) {
            if ("--show".equals(args[i])) {
                showMap = true;
                i++;
            } else if ("--directions".equals(args[i])) {
                if (i + 2 >= args.length) {
                    System.err.println("ERROR: --directions requires start and end intersection IDs.");
                    return;
                }
                startId = args[i + 1];
                endId = args[i + 2];
                i += 3;
            } else {
                System.err.println("Unknown option: " + args[i]);
                return;
            }
        }

        try {
            Graph graph = loadGraph(filename);

            ArrayList<Node> path = null;
            double totalDistance = 0.0;

            if (startId != null && endId != null) {
                Node start = graph.getNode(startId);
                Node end = graph.getNode(endId);

                if (start == null) {
                    System.err.println("Start intersection not found: " + startId);
                    return;
                }
                if (end == null) {
                    System.err.println("End intersection not found: " + endId);
                    return;
                }

                path = graph.shortestPath(start, end);
                if (path == null) {
                    System.out.println("No path found between " + startId + " and " + endId);
                } else {
                    totalDistance = end.getDist();
                    System.out.println("Shortest path from " + startId + " to " + endId + ":");
                    for (Node n : path) {
                        System.out.println(n.getId());
                    }
                    System.out.printf("Total distance: %.3f miles%n", totalDistance);
                }
            }

            if (showMap) {
                final ArrayList<Node> finalPath = path;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame("StreetMap - " + filename);
                        MapPanel panel = new MapPanel(graph);
                        if (finalPath != null) {
                            panel.setPath(finalPath);
                        }
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.add(panel);
                        frame.setSize(800, 800);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                });
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static Graph loadGraph(String filename) throws IOException {
        Graph graph = new Graph();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            if (parts[0].equals("i")) {
                String id = parts[1]; 
                double lat = Double.parseDouble(parts[2]);
                double lon = Double.parseDouble(parts[3]);
                graph.addNode(id, lat, lon);
            } else if (parts[0].equals("r")) {
                String roadId = parts[1];
                String fromId = parts[2];
                String toId = parts[3];
                graph.addUndirectedEdge(roadId, fromId, toId);
            }
        }
        br.close();
        return graph;
    }
}
