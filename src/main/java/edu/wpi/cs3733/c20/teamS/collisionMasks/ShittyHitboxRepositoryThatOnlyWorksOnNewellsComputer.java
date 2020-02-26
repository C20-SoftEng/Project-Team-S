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
            HitboxParser parser = new HitboxParser();
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            for (String line : parser.save(hitboxes)) {
                writer.println(line);
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
            HitboxParser parser = new HitboxParser();
            Scanner scanner = new Scanner(new File(path));
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            return parser.parse(lines);
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
