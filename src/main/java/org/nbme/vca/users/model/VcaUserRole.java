package org.nbme.vca.users.model;

/**
 * Created by rwang on 8/17/2016.
 * This is the user role from VCA view
 */
public enum VcaUserRole {
    PARTICIPANT(0),
    REVIEWER(1),
    SUPERUSER(2),
    ADMIN(3);
    private final int level;
    private VcaUserRole(int level)
    {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
