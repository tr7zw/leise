package dev.tr7zw.leise.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.ResponseHeader;

import io.quarkus.cache.CacheResult;
import io.quarkus.vertx.web.Header;

@Singleton
@Path("/css/{id}")
public class CssProxy {

    private Map<String, URL> mappedImages = new HashMap<String, URL>();

    public CssProxy() {
        registerCss("index", "https://www.heise.de/assets/styles/index.css");
        registerCss("ho", "https://www.heise.de/assets/heise/ho/css/ho.css");
    }

    @GET
    @Produces("text/css")
    @CacheResult(cacheName = "css-cache")
    @ResponseHeader(name = "cache-control", value = "public, max-age=31536000")
    public Response getCss(@PathParam("id") String id) throws IOException {
        URL url = mappedImages.get(id);
        if (url == null) {
            return Response.status(404).build();
        }
        try (BufferedReader text = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            return Response.ok(text.lines().collect(Collectors.joining("\n")))
                    .header("cache-control", "public, max-age=31536000").build();
        }

    }

    public void registerCss(String name, String url) {
        try {
            URL uri = new URL(url);
            mappedImages.put(name, uri);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
