package dev.tr7zw.leiser.server;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import dev.tr7zw.leiser.api.Feed;
import dev.tr7zw.leiser.api.FeedAPI;

@Singleton
@Path("/stats")
public class FeedTest {

    @GET
    public Feed getStats() throws Exception {
        return new FeedAPI().getFeed("https://www.heise.de/developer/rss/news-atom.xml");
    }
    
}
