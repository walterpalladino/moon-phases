package com.whp.moonphases.calculator;

import java.util.HashMap;
import java.util.Map;

/**
 * The Enum MoonPhases.
 *
 * @author	: Walter
 * @date	: 25/11/2012
 * @description	:
 */
public enum MoonPhase {

	/** The new moon. */
	NEW_MOON(0),
	
	/** The waxing crescent moon. */
	WAXING_CRESCENT_MOON(1),
	
	/** The first quarter moon. */
	FIRST_QUARTER_MOON(2),
	
	/** The waxing gibbous moon. */
	WAXING_GIBBOUS_MOON(3),
	
	/** The full moon. */
	FULL_MOON(4),
	
	/** The waning gibbous moon. */
	WANING_GIBBOUS_MOON(5),
	
	/** The third quarter moon. */
	THIRD_QUARTER_MOON(6),
	
	/** The waning crescent moon. */
	WANING_CRESCENT_MOON(7);

	/** The id. */
	private final int id;
	
	private static final Map<Integer, MoonPhase> intToTypeMap = new HashMap<Integer, MoonPhase>();
	static {
	    for (MoonPhase type : MoonPhase.values()) {
	        intToTypeMap.put(type.id, type);
	    }
	}

	/**
	 * Instantiates a new moon phases.
	 *
	 * @param id the id
	 */
	MoonPhase(int id) { this.id = id;}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() { return id; }

	public static MoonPhase fromInt(int i) {
		MoonPhase type = intToTypeMap.get(Integer.valueOf(i));
	    if (type == null) 
	        return null;
	    return type;
	}
	
}
