package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.utilities.Vector2;

import java.io.*;
import java.util.*;

public final class ShittyHitboxRepositoryThatOnlyWorksOnNewellsComputer extends HitboxRepository {
    private static final String path =
            "D:\\Classes\\Software Engineering\\MajorImageEditingEndeavor\\Hitboxes2\\hitboxes.txt";

    @Override
    public boolean canLoad() {
        return true;
    }
    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public void save(Collection<Hitbox> hitboxes) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            for (Hitbox hitbox : hitboxes) {
                writer.println(hitbox.name());
                writer.println(hitbox.floor());

                for (Vector2 vertice : hitbox.vertices()) {
                    writer.print(vertice.x());
                    writer.print(' ');
                    writer.print(vertice.y());
                    writer.print(' ');
                }
                writer.println();
            }
            writer.flush();
            writer.close();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection<Hitbox> load() {
        try {
            List<Hitbox> result = new ArrayList<>();

            for (Scanner scanner = new Scanner(new File(path)); scanner.hasNextLine(); ) {
                Hitbox hitbox = new Hitbox();
                hitbox.setName(scanner.nextLine());
                hitbox.setFloor(Integer.parseInt(scanner.nextLine()));
                hitbox.vertices().addAll(parseVertices(scanner.nextLine()));
                result.add(hitbox);
            }

            return result;
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
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
