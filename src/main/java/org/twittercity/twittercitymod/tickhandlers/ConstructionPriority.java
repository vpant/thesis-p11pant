package org.twittercity.twittercitymod.tickhandlers;

/**
 * Enumeration to set the priority of to be built blocks.
 *
 */
public enum ConstructionPriority {
	BUILD_FIRST(0), BUILD_NORMAL(1), BUILD_LAST(2);
	
	private int id;
	
	ConstructionPriority(int id) {
        this.id = id;
    }
	
	public int getID() {
		return id;
	}
	
	public static ConstructionPriority forID(int id) {
		for (ConstructionPriority priority: values()) {
            if (priority.getID() == id) {
            	return priority;
            }          
        }
		return BUILD_FIRST;
	}
}
