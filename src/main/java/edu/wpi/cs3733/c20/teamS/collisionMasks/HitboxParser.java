package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.utilities.Vector2;

import java.util.*;
import java.util.stream.Stream;

public final class HitboxParser {

    public List<Hitbox> parse(Stream<String> lines) {
        Iterator<String> iter = lines.iterator();
        List<Hitbox> result = new ArrayList<>();

        while (iter.hasNext()) {
            Hitbox hitbox = new Hitbox();
            hitbox.setName(iter.next());
            hitbox.setFloor(Integer.parseInt(iter.next()));
            hitbox.vertices().addAll(parseVertices(iter.next()));
            result.add(hitbox);
        }

        return result;
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
