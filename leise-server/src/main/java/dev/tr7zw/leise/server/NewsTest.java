package dev.tr7zw.leise.server;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.tr7zw.leise.api.ArticleAPI;
import dev.tr7zw.leise.api.Feed;
import dev.tr7zw.leise.api.FeedAPI;
import dev.tr7zw.leise.api.UrlUtil;

@Singleton
@Path("/")
public class NewsTest {

    private final FeedAPI feedAPI = new FeedAPI();
    private final Function<String, String> imageRemapper;
    
    @Inject
    public NewsTest(ImageProxy proxy) {
        imageRemapper = proxy::registerImage;
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getStats() throws Exception {
        return Response.ok(feedAPI.getFeed("https://www.heise.de/developer/rss/news-atom.xml").getOverview(imageRemapper)).build();
    }
    
}
