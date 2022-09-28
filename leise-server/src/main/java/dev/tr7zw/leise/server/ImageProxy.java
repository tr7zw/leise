package dev.tr7zw.leise.server;

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
    private Map<URL, String> reverseMap = new HashMap<URL, String>(); 
    private AtomicInteger counter = new AtomicInteger();
    
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
            if(reverseMap.containsKey(uri)) {
                return "/image/" + reverseMap.get(uri);
            }
            String id = ""+counter.incrementAndGet();
            mappedImages.put(id, uri);
            reverseMap.put(uri, id);
            return "/image/" + id;
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
