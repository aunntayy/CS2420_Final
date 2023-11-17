package flightRoutePlanner;

public class Airport {

	private String code;
	private String name;

	public Airport(String code, String name) {

		this.code = code;
		this.name = name;
	}

	/**
	 * Get code for airport code
	 * 
	 * @return String
	 * 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code for airport
	 * 
	 * @param String 
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get name of the airport
	 * 
	 * @return String
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name for airport 
	 * 
	 * @param String name
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Airport [code=" + code + ", name=" + name + "]";
	}

}
