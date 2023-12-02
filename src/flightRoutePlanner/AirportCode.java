package flightRoutePlanner;

/**
 * 
 * Enum class for airport code.
 * 
 * @authors Chanphone Visathip and Jesse Cherry
 */
public enum AirportCode {
	SEA("Seattle-Tacoma International Airport"), SFO("San Francisco International Airport"),
	LAX("Los Angeles International Airport"), LAS("Harry Reid International Airport"),
	PHX("Phoenix Sky Harbor International Airport"), SLC("Salt Lake City International Airport"),
	DEN("Denver International Airport"), DFW("Dallas/Fort Worth International Airport"),
	IAH("George Bush Intercontinental Airport"), MSP("Minneapolis St Paul International Airport"),
	ORD("O'Hare International Airport"), DTW("Detroit Metropolitan Airport"),
	ATL("Hartsfield Jackson Atlanta International Airport"), JFK("John F. Kennedy International Airport"),
	MIA("Miami International Airport");

	private String name;


	AirportCode(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public String toString() {
	    return name;
	}
}
