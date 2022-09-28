package dev.tr7zw.leise.server;

import java.io.IOException;
import java.net.URL;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/assets/styles/fonts/{id}")
public class FontProxy {

    @GET
    @Produces("font/woff2")
    public Response getImage(@PathParam("id") String id) throws IOException {
        return Response.ok(new URL("https://www.heise.de/assets/styles/fonts/" + id).openStream()).header("cache-control", "public, max-age=31536000").build();
    }

}
