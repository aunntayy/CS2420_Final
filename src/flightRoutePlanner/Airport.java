package flightRoutePlanner;

public class Airport {

	private String code;
	private String name;

	public Airport(String code) {
		this.code = code;

		switch(code) {
			case "SEA":
				this.name = "Seattle International Airport";
			case "SFO":
				this.name = "San Francisco International Airport";
			case "LAX":
				this.name = "Los Angeles International Airport";
			case "LAS":
				this.name = "Harry Reid International Airport";
			case "PHX":
				this.name = "Phoenix Sky Harbor International Airport";
			case "SLC":
				this.name = "Salt Lake City International Airport";
			case "DEN":
				this.name = "Denver International Airport";
			case "DFW":
				this.name = "Dallas Fort Worth International Airport";
			case "IAH":
				this.name = "George Bush Intercontinental Airport";
			case "MSP":
				this.name = "Minneapolis-Saint Paul International Airport";
			case "ORD":
				this.name = "Chicago O'Hare International Airport";
			case "DTW": 
				this.name = "Detroit Metro Airport";
			case "ATL":
				this.name = "Hartsfield-Jackson Atlanta International Airport";
			case "JFK":
				this.name = "John F. Kennedy International Airport";
			case "MIA":
				this.name = "Miami International Airport";
		}
	}

	/**
	 * Get code for airport code
	 * 
	 * @return String
	 * 
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Get name of the airport
	 * 
	 * @return String
	 * 
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "Airport [code=" + code + ", name=" + name + "]";
	}

}
