package ro.utcluj.bpel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * WS-BPEL activities that perform the process logic.
 * 
 * @see <a href="http://docs.oasis-open.org/wsbpel/2.0/wsbpel-specification-draft.html#_Toc143402870">Web Services Business Process Execution Language Version 2.0</a>
 * 
 * @author Florin Pop
 *
 */

public class BPELActivity {

	/**
	 * The name attribute of the activity.
	 */
	public String name;
	
	/**
	 * The list of inner activities.
	 */
	public List<BPELActivity> activities;
		
	/**
	 * The list of inner services invocations.
	 */
	protected List<BPELInvoke> services = null;
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		return name;
	}
	
	/**
	 * Recursively builds the list of inner services invocations.
	 * 
	 * @param result the generated list.
	 * @param activities the current inner activity list.
	 */
	protected void computeServicesList(List<BPELInvoke> result, List<BPELActivity> activities) {
		
		if (activities != null) {
			
			for (BPELActivity activity : activities) {
				
				if (activity instanceof BPELInvoke) {
					
					result.add((BPELInvoke) activity);
				} else {
					
					computeServicesList(result, activity.activities);
				}
			}
		}
	}
	
	/**
	 * Gets the list of inner services invocations.
	 * 
	 * @return the services list.
	 */
	public List<BPELInvoke> services() {
		
		if (services == null) {
			
			services = new ArrayList<BPELInvoke>();
			computeServicesList(services, activities);
		}
		
		return services;
	}
	
	/**
	 * Gets the number of inner services invocations.
	 * 
	 * @return the services invocations count.
	 */
	public int servicesCount() {
		
		if (services() == null) {
			
			return 0;
		}
				
		return services().size();
	}
	
	/**
	 * 
	 * Calculates the aggregate QoS.
	 * 
	 * @param activity the activity for which the aggregate QoS is evaluated.
	 * @param servicesQoS the map of services QoS models.
	 * 
	 * @return the aggregated QoS model.
	 */
	public QoSModel aggregateQos(BPELActivity activity, Map<BPELInvoke, QoSModel> servicesQoS) {
		
		QoSModel result = QoSModel.emptyQoSModel();
		
		if (activity instanceof BPELProcess ||
				activity instanceof BPELSequence) {
			
			if (activity.activities == null)
				return result;
			
			for (BPELActivity a : activity.activities) {
				
				QoSModel cQoS = aggregateQos(a, servicesQoS);
				// sum
				result.setCost(result.getCost() + cQoS.getCost());
				// prod
				result.setRating(result.getRating() * cQoS.getRating());
				// sum
				result.setResponseTime(result.getResponseTime() + cQoS.getResponseTime());
			}
			
			
		} else if (activity instanceof BPELFlow) {
			
			for (BPELActivity a : activity.activities) {
				
				QoSModel cQoS = aggregateQos(a, servicesQoS);
				
				// sum
				result.setCost(result.getCost() + cQoS.getCost());
				// prod
				result.setRating(result.getRating() * cQoS.getRating());
				// max
				result.setResponseTime(Math.max(result.getResponseTime(), cQoS.getResponseTime()));
			}
		} else if (activity instanceof BPELCondition) {
			
			BPELCondition condition = (BPELCondition) activity;
			
			for (BPELActivity a : activity.activities) {
				
				QoSModel cQoS = aggregateQos(a, servicesQoS);
				
				if (!(a instanceof BPELCondition)) {
									
					// sum prob
					result.setCost(result.getCost() + condition.probability() * cQoS.getCost());
					// sum prob
					result.setRating(result.getRating() + condition.probability() * cQoS.getRating());
					// sum prob
					result.setResponseTime(result.getResponseTime() + condition.probability() * cQoS.getResponseTime());
				} else {
					
					// sum
					result.setCost(result.getCost() + cQoS.getCost());
					// sum
					result.setRating(result.getRating() + cQoS.getRating());
					// sum
					result.setResponseTime(result.getResponseTime() + cQoS.getResponseTime());
				}
								
			}
		} else if (activity instanceof BPELInvoke) {
			
			return servicesQoS.get(activity);
		}
		
		return result;
	}
}
