package edu.wpi.cs3733.c20.teamS.collisionMasks;

import java.util.Collection;

public abstract class HitboxRepository {
    public abstract Collection<Hitbox> load();
    public abstract void save(Collection<Hitbox> hitboxes);
    public abstract boolean canLoad();
    public abstract boolean canSave();
}
