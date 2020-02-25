package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.utilities.Vector2;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class HitboxParser {
    public List<Hitbox> parse(Iterable<String> lines) {

        List<Hitbox> result = new ArrayList<>();
        Iterator<String> iter = lines.iterator();

        if (!iter.hasNext())
            return result;
        int linesPerHitbox = Integer.parseInt(iter.next());
        while (iter.hasNext()) {
            switch (linesPerHitbox) {
                case 3:
                    result.add(parseThreeLineFormat(iter));
                    break;
                case 4:
                    result.add(parseFourLineFormat(iter));
                    break;
                default:
                    throw new RuntimeException("Unexpected number of lines per hitbox file format.");
            }
        }

        return result;
    }

    public List<String> save(Iterable<Hitbox> hitboxes) {
        Stream<String> hitboxLines = StreamSupport.stream(hitboxes.spliterator(), false)
                .map(hitbox -> Arrays.asList(
                        hitbox.name(),
                        Integer.toString(hitbox.floor()),
                        saveVertices(hitbox.vertices()),
                        saveTouchingNodes(hitbox.touchingNodes())
                ))
                .flatMap(lines -> lines.stream());

        return Stream.concat(Stream.of("4"), hitboxLines)
                .collect(Collectors.toList());
    }

    private Hitbox parseThreeLineFormat(Iterator<String> iter) {
        Hitbox hitbox = new Hitbox();
        hitbox.setName(iter.next());
        hitbox.setFloor(Integer.parseInt(iter.next()));
        hitbox.vertices().addAll(parseVertices(iter.next()));

        return hitbox;
    }

    private Hitbox parseFourLineFormat(Iterator<String> iter) {
        Hitbox hitbox = parseThreeLineFormat(iter);
        for (String nodeID : iter.next().split("\\s"))
            hitbox.touchingNodes().add(nodeID);

        return hitbox;
    }

    private String saveVertices(Iterable<Vector2> vertices) {
        StringBuilder sb = new StringBuilder();
        for (Vector2 vertex : vertices) {
            sb.append(vertex.x());
            sb.append(' ');
            sb.append(vertex.y());
            sb.append(' ');
        }

        return sb.toString();
    }

    private String saveTouchingNodes(Iterable<String> nodeIDs) {
        StringBuilder sb = new StringBuilder();
        for (String id : nodeIDs) {
            sb.append(id);
            sb.append(' ');
        }

        return sb.toString();
    }

    private List<Vector2> parseVertices(String line) {
        List<String> words = Arrays.asList(line.split("\\s"));
        List<Vector2> result = new ArrayList<>(words.size() / 2);

        Iterator<String> iter = words.iterator();
        while (iter.hasNext()) {
            Vector2 vertex = new Vector2(
                    Double.parseDouble(iter.next()),
                    Double.parseDouble(iter.next())
            );
            result.add(vertex);
        }

        return result;
    }
}
