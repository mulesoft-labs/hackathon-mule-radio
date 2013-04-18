package org.mule.hackathon.agent;

import com.sun.jersey.api.container.filter.LoggingFilter;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;
import org.mule.hackathon.indexer.Indexer;

import javax.ws.rs.core.UriBuilder;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class in charge of initializing the Agent
 * It will index all the music files under configured base folder and register the agent in the server
 */
public class AgentInitialize {

    private static Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    public static void main(String args[]) {
        Indexer index = AgentIndexer.getIndex();
        index.indexFolder(AgentUtils.getProperties().getBaseFolder());
        int status = 0;

        for (int i=1;i<=5;i++) {
            AgentRegistration registration = new AgentRegistration();
            status = registration.registerToServer(index.getAgentIndexRecordList());

            if (status == 200) break;
            logger.log(Level.WARNING, String.format("%s retry to connect to Server %s", i,
                    AgentUtils.getProperties().getServerUrl()));
        }

        if (status != 200) {
            logger.log(Level.SEVERE, String.format("Impossible to register the Agent in the Server %s",
                    AgentUtils.getProperties().getServerUrl()));
        }

        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(AgentUtils.getProperties().getAgentUrl()).build();
    }

    protected static Closeable startServer() throws IOException {
        ResourceConfig rc = new DefaultResourceConfig();
        rc.getSingletons().add(new AgentStreamingResource());
        rc.getSingletons().add(new AgentPingResource());
        rc.getFeatures().put(LoggingFilter.FEATURE_LOGGING_DISABLE_ENTITY, true);
        rc.getProperties().put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS, LoggingFilter.class.getName());
        rc.getProperties().put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, LoggingFilter.class.getName());

        return SimpleServerFactory.create(getBaseURI(), rc);
    }
}
