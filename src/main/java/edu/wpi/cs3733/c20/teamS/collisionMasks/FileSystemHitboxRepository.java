package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.io.*;
import java.util.*;

public final class FileSystemHitboxRepository extends HitboxRepository {
    private final String path;

    public FileSystemHitboxRepository(String path) {
        if (path == null) ThrowHelper.illegalNull("path");

        this.path = path;
    }

    @Override
    public boolean canLoad() {
        return true;
    }
    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public void save(Collection<Room> rooms) {
        try {
            HitboxParser parser = new HitboxParser();
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            for (String line : parser.save(rooms)) {
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
    public Collection<Room> load() {
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
