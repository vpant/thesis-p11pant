package org.twittercity.twittercitymod.tickhandlers;

public enum ConstructionAction {
    DESTROY(0), BUILD(1);

    private final int id;

    ConstructionAction(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public static ConstructionAction forID(int id) {
        for (ConstructionAction action: values()) {
            if (action.getID() == id) {
                return action;
            }
        }
        return BUILD;
    }
}
