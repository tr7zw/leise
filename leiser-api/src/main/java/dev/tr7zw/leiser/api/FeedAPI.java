package dev.tr7zw.leiser.api;

import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import dev.tr7zw.leiser.api.Feed.FeedBuilder;

public class FeedAPI {

    public Feed getFeed(String url) throws Exception {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        FeedBuilder feedBuilder = Feed.builder().title(feed.getTitle());
        for (SyndEntry entry : feed.getEntries()) {
            feedBuilder.entry(Entry.builder().title(entry.getTitle()).description(entry.getDescription().getValue())
                    .url(entry.getUri()).previewImage(getPreviewUrl(entry.getContents())).build());
        }
        return feedBuilder.build();
    }

    private String getPreviewUrl(List<SyndContent> list) {
        for (SyndContent cont : list) {
            if ("html".equals(cont.getType())) {
                Element el = Jsoup.parse(cont.getValue()).getElementsByAttribute("src").first();
                if (el != null) {
                    return el.attr("src");
                }
            }
        }
        return null;
    }

}
