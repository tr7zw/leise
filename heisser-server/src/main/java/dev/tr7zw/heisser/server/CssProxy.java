package dev.tr7zw.heisser.server;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
    public Response getImage(@PathParam("id") String id) throws IOException {
        URL url = mappedImages.get(id);
        if(url == null) {
            return Response.status(404).build();
        }
        return Response.ok(url.openStream()).build();
    }
    
    public void registerCss(String name, String url) {
        try {
            URL uri = new URL(url);
            mappedImages.put(name, uri);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
