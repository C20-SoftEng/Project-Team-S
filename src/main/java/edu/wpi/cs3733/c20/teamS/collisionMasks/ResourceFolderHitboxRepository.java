package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.utilities.Vector2;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ResourceFolderHitboxRepository extends HitboxRepository {
    private static final String path = "/data/hitboxes.txt";

    @Override
    public Collection<Hitbox> load() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(path))
        );

        return triplets(reader.lines()).stream()
                .map(this::parseTriplet)
                .collect(Collectors.toList());
    }

    private List<String[]> triplets(Stream<String> lines) {
        Iterator<String> iterator = lines.iterator();
        List<String[]> result = new ArrayList<>();

        while (iterator.hasNext()) {
            String[] triplet = new String[] {
                    iterator.next(),
                    iterator.next(),
                    iterator.next()
            };
            result.add(triplet);
        }

        return result;
    }
    private Hitbox parseTriplet(String[] triplet) {
        assert triplet.length == 3;

        Hitbox result = new Hitbox();
        result.setName(triplet[0]);
        result.setFloor(Integer.parseInt(triplet[1]));

        Iterator<String> iter = Arrays.asList(triplet[2].split("\\s")).iterator();
        while (iter.hasNext()) {
            Vector2 vertex = new Vector2(
                    Double.parseDouble(iter.next()),
                    Double.parseDouble(iter.next())
            );
            result.vertices().add(vertex);
        }

        return result;
    }

    /**
     * Throws an UnsupportedOperationException, as saving to resource folder is not allowed.
     * @throws UnsupportedOperationException always.
     */
    @Override
    public void save(Collection<Hitbox> hitboxes) {
        throw new UnsupportedOperationException("Saving hitboxes to resource folder is not supported.");
    }

    @Override
    public boolean canLoad() {
        return true;
    }

    @Override
    public boolean canSave() {
        return false;
    }
}
