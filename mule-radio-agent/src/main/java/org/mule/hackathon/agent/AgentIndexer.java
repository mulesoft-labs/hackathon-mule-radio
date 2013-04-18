package org.mule.hackathon.agent;

import org.mule.hackathon.indexer.Indexer;

/**
 */
public class AgentIndexer {

    private static Indexer index;

    public static synchronized Indexer getIndex() {
        if (index == null) {
            index = new Indexer();
            index.setBaseUri(AgentUtils.getProperties().getAgentUrl() +
                    AgentUtils.getProperties().getAgentMediaUrlPrefix());
        }

        return index;
    }
}
