package org.mule.hackathon.agent;

/**
 *  Resource for streaming the files in the client
 */

import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;

@Path("streaming")
@Singleton
public class AgentStreamingResource {

    final int chunk_size = 1024 * 1024;

    public AgentStreamingResource() {
    }

    @GET
    @Produces("audio/mp3")
    public Response streamAudio(@HeaderParam("Range") String range, @QueryParam("file") String file) throws Exception {
        if (AgentIndexer.getIndex().contains(file)) {
            return buildStream(AgentIndexer.getIndex().find(file).getPathLocal(), range);
        }

        return buildExceptionResponse();
    }

    /**
     * @param file Media file
     * @param range range header
     * @return Streaming output
     * @throws Exception IOException if an error occurs in streaming.
     */
    private Response buildStream(final String file, final String range) throws Exception {
        final File asset = new File(file);

        if (range == null) {
            StreamingOutput streamer = new StreamingOutput() {
                @Override
                public void write(final OutputStream output) throws IOException, WebApplicationException {

                    final FileChannel inputChannel = new FileInputStream(asset).getChannel();
                    final WritableByteChannel outputChannel = Channels.newChannel(output);
                    try {
                        inputChannel.transferTo(0, inputChannel.size(), outputChannel);
                    } finally {
                        inputChannel.close();
                        outputChannel.close();
                    }
                }
            };
            return Response.ok(streamer).status(200).header(HttpHeaders.CONTENT_LENGTH, asset.length()).build();
        }

        String[] ranges = range.split("=")[1].split("-");
        final int from = Integer.parseInt(ranges[0]);

        int to = chunk_size + from;
        if (to >= asset.length()) {
            to = (int) (asset.length() - 1);
        }
        if (ranges.length == 2) {
            to = Integer.parseInt(ranges[1]);
        }

        final String responseRange = String.format("bytes %d-%d/%d", from, to, asset.length());
        final RandomAccessFile raf = new RandomAccessFile(asset, "r");
        raf.seek(from);

        final int len = to - from + 1;
        final AgentStreamer streamer = new AgentStreamer(len, raf);
        Response.ResponseBuilder res = Response.ok(streamer).status(206)
                .header("Accept-Ranges", "bytes")
                .header("Content-Range", responseRange)
                .header(HttpHeaders.CONTENT_LENGTH, streamer.getLenth())
                .header(HttpHeaders.LAST_MODIFIED, new Date(asset.lastModified()));
        return res.build();
    }

    private Response buildExceptionResponse() {
        return Response.serverError().status(500)
                .header("Accept-Ranges", "bytes").build();
    }
}
