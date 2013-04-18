package org.mule.hackathon.agent;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.mule.hackathon.agent.model.AgentIndex;
import org.mule.hackathon.agent.model.AgentIndexRecord;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Class in charge of registrating the agent in the Mule server
 * It sends the AgentIndex object to be indexed in the server
 */
public class AgentRegistration {

    private Client client;

    public int registerToServer(List<AgentIndexRecord> agentIndexRecordList) {
        AgentIndex agentIndex = new AgentIndex();
        agentIndex.setAgentId(AgentUtils.getProperties().getAgentUrl());
        agentIndex.setIndexRecords(agentIndexRecordList);
        String serverUrl = AgentUtils.getProperties().getServerUrl();

        ClientResponse response = getClient().resource(serverUrl)
                .entity(AgentUtils.objectToJson(agentIndex))
                .type(MediaType.TEXT_PLAIN_TYPE)
                .post(ClientResponse.class);

        return response.getStatus();
    }

    public Client getClient() {
        if (client == null) {
            client = new Client();
        }

        return this.client;
    }

}
