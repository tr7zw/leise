package dev.tr7zw.leise.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import io.quarkus.cache.CacheResult;

@Singleton
@Path("/favicon.ico")
public class FaviconProxy {

    @GET
    @Produces("image/x-icon")
    @CacheResult(cacheName = "icon-cache")
    public Response getIcon() throws IOException {
        URL url = new URL("https://www.heise.de/favicon.ico");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        url.openConnection().getInputStream().transferTo(buffer);
        return Response.ok(buffer.toByteArray()).header("cache-control", "public, max-age=31536000").build();
    }

}
