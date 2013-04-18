package org.mule.hackathon.agent;

/**
 *  Resource for checking if the agent is up
 */

import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("ping")
@Singleton
public class AgentPingResource {

    public AgentPingResource() {
    }

    @GET
    public Response ping() throws Exception {
        Response.ResponseBuilder res = Response.ok().status(200);
        return res.build();
    }
}
