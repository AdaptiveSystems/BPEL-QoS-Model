package ro.utcluj.bpel;

/**
 * 
 * An activity that provides conditional behavior.
 * 
 * @author Florin Pop
 *
 */
public class BPELCondition extends BPELActivity {

	/**
	 * The probability to execute this conditional branch.
	 */
	private String probability;

	/**
	 * Gets the probability to execute this conditional branch.
	 * 
	 * @return the probability.
	 */
	public double probability() {
		
		if (probability == null)
			return 1.0D;
		
		return Double.parseDouble(probability);
	}
	
}
