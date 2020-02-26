package edu.wpi.cs3733.c20.teamS.collisionMasks;

import java.util.Collection;

public abstract class HitboxRepository {
    public abstract Collection<Room> load();
    public abstract void save(Collection<Room> rooms);
    public abstract boolean canLoad();
    public abstract boolean canSave();
}
