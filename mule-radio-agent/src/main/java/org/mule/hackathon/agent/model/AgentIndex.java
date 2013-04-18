package org.mule.hackathon.agent.model;

import org.mule.hackathon.agent.model.AgentIndexRecord;

import java.io.Serializable;
import java.util.List;

/**
 */
public class AgentIndex implements Serializable {

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
