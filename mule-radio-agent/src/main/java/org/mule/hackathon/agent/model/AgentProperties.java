package org.mule.hackathon.agent.model;

/**
 */
public class AgentProperties {

    private String serverUrl;
    private String baseFolder;
    private String agentUrl;
    private String agentMediaUrlPrefix;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getBaseFolder() {
        return baseFolder;
    }

    public void setBaseFolder(String baseFolder) {
        this.baseFolder = baseFolder;
    }

    public String getAgentUrl() {
        return agentUrl;
    }

    public void setAgentUrl(String agentUrl) {
        this.agentUrl = agentUrl;
    }

    public String getAgentMediaUrlPrefix() {
        return agentMediaUrlPrefix;
    }

    public void setAgentMediaUrlPrefix(String agentMediaUrlPrefix) {
        this.agentMediaUrlPrefix = agentMediaUrlPrefix;
    }
}
