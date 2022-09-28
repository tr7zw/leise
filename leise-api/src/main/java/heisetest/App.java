package heisetest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import dev.tr7zw.leise.api.ArticleAPI;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IllegalArgumentException, MalformedURLException, FeedException, IOException
    {

    }
    
    public static String getPreviewUrl(List<SyndContent> list) {
        for(SyndContent cont : list) {
            if("html".equals(cont.getType())) {
                Element el = Jsoup.parse(cont.getValue()).getElementsByAttribute("src").first();
                if(el != null) {
                    return el.attr("src");
                }
            }
        }
        return null;
    }
    
}
