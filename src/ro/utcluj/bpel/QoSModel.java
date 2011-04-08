package ro.utcluj.bpel;

import java.text.DecimalFormat;

/**
 * 
 * A simple QoS model for Web Services. 
 * It contains the following QoS properties:
 * <ul>
 * <li>Response time</li> 
 * <li>Cost</li> 
 * <li>Rating</li> 
 * </ul>
 * @author Florin Pop
 *
 */
public class QoSModel {

	/**
	 * The response time QoS property.
	 */
	protected double responseTime;
	
	/**
	 * The cost QoS property.
	 */
	protected double cost;
	
	/**
	 * The rating QoS property.
	 */
	protected double rating;
	
	/**
	 * Gets the response time QoS property.
	 * 
	 * @return the response time.
	 */
	public double getResponseTime() {
		return responseTime;
	}

	/**
	 * Sets the response time QoS property.
	 * 
	 * @param responseTime the response time.
	 */
	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}

	/**
	 * Gets the cost QoS property.
	 * 
	 * @return the cost.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Sets the cost QoS property.
	 * 
	 * @param cost the cost.
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Gets the rating QoS property.
	 * 
	 * @return the rating.
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * Sets the rating QoS property.
	 * 
	 * @param rating the rating.
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}
		
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		DecimalFormat doubleFormat = new DecimalFormat("#.##");
		
		return "[c=" + doubleFormat.format(cost) + ", r=" + doubleFormat.format(rating) + ", t=" + doubleFormat.format(responseTime) + "]"; // + ", p=" + twoDForm.format(probability) + ", k=" + multiplier + "]";
	}

	/**
	 * Generates an empty QoS model.
	 * 
	 * @return the empty QoS model.
	 */
	public static QoSModel emptyQoSModel() {
		
		QoSModel result = new QoSModel();
		
		result.setRating(1);
		
		return result;
	}
}
