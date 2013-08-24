package com.whp.moonphases.calculator;

import java.util.Calendar;

public class MoonCalculator {

	/*
	public static MoonPhase getPhase(Calendar date) {
		
//		int day		= date.get(Calendar.DATE);        
//		int month	= date.get(Calendar.MONTH) + 1;        
		int year	= date.get(Calendar.YEAR);        
//		int dow		= date.get(Calendar.DAY_OF_WEEK);        
//		int dom		= date.get(Calendar.DAY_OF_MONTH);        
		int doy		= date.get(Calendar.DAY_OF_YEAR);
		
		 int	cent	= (year / 100) + 1;                  // Century number
	     int	golden	= (year % 19) + 1;                   // Golden number
	     int	epact	= ((11 * golden) + 20                // Golden number
	    		 		+ (((8 * cent) + 5) / 25) - 5        // 400 year cycle
	    		 		- (((3 * cent) / 4) - 12)) % 30;     //Leap year correction
	     if (epact <= 0)
	    	 epact += 30;                        			 // Age range is 1 .. 30
	     if ((epact == 25 && golden > 11) || 
	    	 (epact == 24)) {
	    	 epact++;
	     }

	     int	phase;
	     phase = (((((doy + epact) * 6) + 11) % 177) / 22) & 7;
	        
	     int moonDay	= (((((doy + epact) * 6) + 11) % 177) / 22) ;
	     
		return MoonPhase.fromInt(phase);
		
	}
	*/
	
	public static final double MOON_PHASE_LENGTH = 29.530588853;
	
	public static double getPhase(Calendar date) {
		
		int year = date.get(Calendar.YEAR);     
		int month = date.get(Calendar.MONTH) + 1;     
		int day = date.get(Calendar.DAY_OF_MONTH);         
		
		// Convert the year into the format expected by the algorithm.    
		double transformedYear = year - Math.floor((12 - month) / 10);    

		// Convert the month into the format expected by the algorithm.    
		int transformedMonth = month + 9;    
		if (transformedMonth >= 12) {      
			transformedMonth = transformedMonth - 12;    
		}
		
		// Logic to compute moon phase as a fraction between 0 and 1    
		double term1 = Math.floor(365.25 * (transformedYear + 4712));    
		double term2 = Math.floor(30.6 * transformedMonth + 0.5);    
		double term3 = Math.floor(Math.floor((transformedYear / 100) + 49) * 0.75) - 38;        
		double intermediate = term1 + term2 + day + 59;    
		if (intermediate > 2299160) {      
			intermediate = intermediate - term3;    
		}       
		double normalizedPhase = (intermediate - 2451550.1) / MOON_PHASE_LENGTH;    
		normalizedPhase = normalizedPhase - Math.floor(normalizedPhase);    
		if (normalizedPhase < 0) {      
			normalizedPhase = normalizedPhase + 1;    
		}     
       
		// Return the result as a value between 0 and MOON_PHASE_LENGTH    
		return normalizedPhase ;//* MOON_PHASE_LENGTH; 
//		double phaseTmp	= normalizedPhase * MOON_PHASE_LENGTH ;
//		int phaseValue = ((int) Math.floor(phaseTmp)) % 30;
//		
//		int phase = (int) (normalizedPhase * 8.0);
//		return MoonPhase.fromInt(phase);
		
	}
	
		
	
}
