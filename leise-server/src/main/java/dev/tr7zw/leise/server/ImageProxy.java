package dev.tr7zw.leise.server;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    @GET
    @Produces("image/png")
    public Response getImage(@PathParam("id") String id) throws IOException {
        URL url = mappedImages.get(id);
        if (url == null) {
            return Response.status(404).build();
        }
        return Response.ok(url.openStream()).header("cache-control", "public, max-age=31536000").build();
    }

    public String registerImage(String url) {
        try {
            URL uri = new URL(url);
            if (reverseMap.containsKey(uri)) {
                return "/image/" + reverseMap.get(uri);
            }
            String id = getMd5(url);
            mappedImages.put(id, uri);
            reverseMap.put(uri, id);
            return "/image/" + id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String getMd5(String url) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(url.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for(byte b: digest)
               sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

}
