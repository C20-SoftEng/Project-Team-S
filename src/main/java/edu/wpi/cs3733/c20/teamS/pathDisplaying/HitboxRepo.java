package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import edu.wpi.cs3733.c20.teamS.Editing.NodeHitbox;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.shape.Polygon;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public final class HitboxRepo {
    private static final String path = "/data/nodeHitboxes.txt";
    private static final char NODE_ID_CHAR = '#';

    private File getFileFromResources() {
        URL url = getClass().getClassLoader().getResource(path);
        return new File(url.getFile());
    }

    public Set<NodeHitbox> loadHitboxes(Collection<NodeData> nodes)  {
        Map<String, NodeData> nodeIDMap = nodes.stream()
                .collect(Collectors.toMap(node -> node.getNodeID(), node -> node));
        BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(path)
        ));

        Set<NodeHitbox> results = new HashSet<>();
        NodeHitbox underConstruction = null;

        for (String line : br.lines().collect(Collectors.toList())) {
            if (line.charAt(0) == NODE_ID_CHAR) {
                String id = line.substring(1);
                underConstruction = new NodeHitbox(nodeIDMap.get(id), new Polygon());
                results.add(underConstruction);
                continue;
            }

            double coordinate = Double.parseDouble(line);
            underConstruction.mask().getPoints().add(coordinate);
        }

        return results;

    }
}
