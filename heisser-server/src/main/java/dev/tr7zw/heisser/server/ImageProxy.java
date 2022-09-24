package dev.tr7zw.heisser.server;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/image/{id}")
public class ImageProxy {

    private Map<String, URL> mappedImages = new HashMap<String, URL>(); 
    private AtomicInteger counter = new AtomicInteger();
    
    public ImageProxy() {
        registerImage("https://www.heise.de/scale/geometry/450/q80//imgs/18/3/6/1/4/0/4/0/Janus-c00e8813b067a0a6.jpeg");
    }
    
    @GET
    @Produces("image/png")
    public Response getImage(@PathParam("id") String id) throws IOException {
        URL url = mappedImages.get(id);
        if(url == null) {
            return Response.status(404).build();
        }
        return Response.ok(url.openStream()).build();
    }
    
    public String registerImage(String url) {
        try {
            URL uri = new URL(url);
            String id = ""+counter.incrementAndGet();
            mappedImages.put(id, uri);
            return "/image/" + id;
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
