package org.nbme.vca.users.model;

/**
 * Created by rwang on 8/17/2016.
 * This is the user role from VCA view
 */
public enum VcaUserRole {
    PARTICIPANT(0,"Participant"),
//    REVIEWER(1,"Reviewer"),
//    SUPERUSER(2,"Super user"),
    ADMIN(3,"Administratior");
    private final int level;
    private final String displayName;
    private VcaUserRole(int level, String name)
    {
        this.level = level;
        this.displayName = name;
    }

    public int getLevel() {
        return level;
    }
    
    public String getDisplayName(){
    	return this.displayName;
    }
}
