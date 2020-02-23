package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.MutableGraph;
import com.sun.javafx.scene.text.HitInfo;
import edu.wpi.cs3733.c20.teamS.Editing.NodeHitbox;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dedicated to my lifelong friends who taught me how to write shitty code that works.
 */
public final class SuperShittyHitboxSaver {
    private static final char NODE_ID_SEQUENCE = '#';
    private static final String path = "D:\\Classes\\Software Engineering\\MajorImageEditingEndeavor\\Node Hitboxes.txt";

    public void shittySave(Iterable<NodeHitbox> hitboxes) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printer = new PrintWriter(fileWriter);
        for (NodeHitbox hitbox : hitboxes) {
            printer.println(NODE_ID_SEQUENCE + hitbox.node().getNodeID());
            for (double coordinate : hitbox.mask().getPoints()) {
                printer.println(coordinate);
            }
        }
        printer.flush();
        printer.close();
    }

    public Set<NodeHitbox> shittyLoad(Collection<NodeData> nodes) {
        Map<String, NodeData> nodeMap = nodes.stream()
                .collect(Collectors.toMap(
                        node -> node.getNodeID(),
                        node -> node
                ));
        List<String> lines = readLines(new File(path));
        Iterator<String> iter = lines.iterator();
        Set<NodeHitbox> result = new HashSet<>();
        if (!iter.hasNext())
            return result;
        NodeHitbox underConstruction = null;
        while (iter.hasNext()) {
            String line = iter.next();
            if (line.charAt(0) == NODE_ID_SEQUENCE) {
                underConstruction = new NodeHitbox(nodeMap.get(line.substring(1)), new Polygon());
                underConstruction.mask().setFill(
                        Color.DEEPPINK.deriveColor(1, 1, 1, .4));
                result.add(underConstruction);
                continue;
            }
            double coordinate = Double.parseDouble(line);
            underConstruction.mask().getPoints().add(coordinate);
        }

        return result;
    }

    private List<String> readLines(File file) {
        try {
            Scanner scanner = new Scanner(file);
            List<String> result = new ArrayList<>();
            while (scanner.hasNextLine())
                result.add(scanner.nextLine());
            return result;
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
