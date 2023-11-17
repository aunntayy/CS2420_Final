package flightRoutePlanner;

public class Flight {

	private Airport depart;
	private Airport destination;
	private int flightTime;
	private int cost;

	public Flight(Airport depart, Airport destination, int flightTime, int cost) {

		this.depart = depart;
		this.destination = destination;
		this.flightTime = flightTime;
		this.cost = cost;
	}

	/**
	 * Get depart airport
	 * 
	 * @return depart airport
	 */
	public Airport getDepart() {
		return depart;
	}

	/**
	 * Set depart airport
	 * 
	 * @param depart
	 */
	public void setDepart(Airport depart) {
		this.depart = depart;
	}

	/**
	 * Get destination
	 * 
	 * @return destination airport
	 */
	public Airport getDestination() {
		return destination;
	}

	/**
	 * Set destination
	 * 
	 * @param destination
	 */
	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	/**
	 * Get fligh time
	 * 
	 * @return flight time
	 */
	public int getFlightTime() {
		return flightTime;
	}

	/**
	 * Set flight time
	 * 
	 * @param flightTime
	 */
	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

	/**
	 * Get cost of flight
	 * 
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Set cost of flight
	 * 
	 * @param void
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

}
