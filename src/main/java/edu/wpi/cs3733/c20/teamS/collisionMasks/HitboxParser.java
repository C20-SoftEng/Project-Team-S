package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class HitboxParser {
    public List<Room> parse(Iterable<String> lines) {

        List<Room> result = new ArrayList<>();
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
                case 7:
                    result.add(parseSevenLineFormat(iter));
                    break;
                default:
                    throw new RuntimeException("Unexpected number of lines per hitbox file format.");
            }
        }

        return result;
    }

    public List<String> save(Iterable<Room> rooms) {
        Stream<String> lines = StreamSupport.stream(rooms.spliterator(), false)
                .map(this::saveSingleRoom)
                .flatMap(Collection::stream);
        return Stream.concat(Stream.of("7"), lines)
                .collect(Collectors.toList());
    }

    private List<String> saveSingleRoom(Room room) {
        List<String> result = new ArrayList<>();
        result.add(room.name());
        result.add(Integer.toString(room.floor()));
        result.add(saveVertices(room.vertices()));
        result.add(saveTouchingNodes(room.touchingNodes()));
        result.add(room.icon());
        result.addAll(saveDescription(room.description()));

        return result;
    }
    private List<String> saveDescription(String description) {
        String[] lines = description.split("[\\r\\n]+");
        List<String> result = new ArrayList<>();
        result.add(Integer.toString(lines.length));
        Collections.addAll(result, lines);

        return result;
    }

    public List<String> saveFourLineFormat(Iterable<Room> hitboxes) {
        Stream<String> hitboxLines = StreamSupport.stream(hitboxes.spliterator(), false)
                .map(hitbox -> Arrays.asList(
                        hitbox.name(),
                        Integer.toString(hitbox.floor()),
                        saveVertices(hitbox.vertices()),
                        saveTouchingNodes(hitbox.touchingNodes())
                ))
                .flatMap(Collection::stream);

        return Stream.concat(Stream.of("4"), hitboxLines)
                .collect(Collectors.toList());
    }

    private Room parseThreeLineFormat(Iterator<String> iter) {
        Room room = new Room();
        room.setName(iter.next());
        room.setFloor(Integer.parseInt(iter.next()));
        room.vertices().addAll(parseVertices(iter.next()));

        return room;
    }

    private Room parseFourLineFormat(Iterator<String> iter) {
        Room room = parseThreeLineFormat(iter);
        for (String nodeID : iter.next().split("\\s"))
            room.touchingNodes().add(nodeID);

        return room;
    }

    private Room parseSevenLineFormat(Iterator<String> iter) {
        Room room = parseFourLineFormat(iter);
        room.setIcon(iter.next());
        int descriptionLineCount = Integer.parseInt(iter.next());
        StringBuilder description = new StringBuilder();
        for (int count = 0; count < descriptionLineCount; count++) {
            description.append(iter.next());
            description.append("\n");
        }
        room.setDescription(description.toString());

        return room;
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
