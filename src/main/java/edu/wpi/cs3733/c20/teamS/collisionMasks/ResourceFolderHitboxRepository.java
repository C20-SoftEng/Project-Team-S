package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.utilities.Vector2;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

        HitboxParser parser = new HitboxParser();
        return parser.parse(() -> reader.lines().iterator());
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
