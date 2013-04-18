package org.mule.hackathon.agent;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.mule.hackathon.agent.model.AgentProperties;

import java.io.IOException;
import java.util.Properties;

/**
 */
public class AgentUtils {

    private static AgentProperties AGENT_PROPERTIES;
    private static String AGENT_PROPERTIES_FILE = "agent.properties";

    public static synchronized AgentProperties getProperties() {
        if (AGENT_PROPERTIES == null) {
            AGENT_PROPERTIES = new AgentProperties();
            loadProperties();
        }

        return AGENT_PROPERTIES;
    }

    private static void loadProperties() {
        try {
            Properties properties = new Properties();
            properties.load(AgentUtils.class.getClassLoader().getResourceAsStream(AGENT_PROPERTIES_FILE));

            AGENT_PROPERTIES.setAgentUrl(properties.getProperty("agent.url"));
            AGENT_PROPERTIES.setBaseFolder(properties.getProperty("agent.media.baseFolder"));
            AGENT_PROPERTIES.setServerUrl(properties.getProperty("server.url"));
            AGENT_PROPERTIES.setAgentMediaUrlPrefix(properties.getProperty("agent.media.url.prefix"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonMappingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JsonGenerationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
