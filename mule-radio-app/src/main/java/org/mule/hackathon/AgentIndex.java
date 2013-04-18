package org.mule.hackathon;

import java.io.Serializable;
import java.util.List;

/**
 */
public class AgentIndex implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6360934696503132014L;
	
	private String agentId;
	private List<AgentIndexRecord> indexRecords;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public List<AgentIndexRecord> getIndexRecords() {
		return indexRecords;
	}

	public void setIndexRecords(List<AgentIndexRecord> indexRecords) {
		this.indexRecords = indexRecords;
	}
}
