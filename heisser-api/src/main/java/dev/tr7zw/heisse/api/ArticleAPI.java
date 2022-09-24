package dev.tr7zw.heisse.api;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ArticleAPI {

    private Function<String, String> urlRemap;
    
    public ArticleAPI(Function<String, String> urlRemap) {
        this.urlRemap = urlRemap;
    }
    
    public String getArticle(int id) throws IOException {
        Document doc = Jsoup.connect("https://www.heise.de/-" + id + "?seite=all").followRedirects(true).get();
        Element report = doc.getElementById("meldung");
        report.appendElement("link").attr("rel", "stylesheet").attr("href", "/css/index");
        report.appendElement("link").attr("rel", "stylesheet").attr("href", "/css/ho");
        filterByClass(report, "article-layout__header-wrbng");
        filterByClass(report, "article-sidebar");
        filterByClass(report, "a-ad");
        filterByAttributeValue(report, "name", "meldung.ho.header.drucken");
        filterByAttributeValue(report, "data-component", "RecommendationBox");
        filterByAttributeValueStarting(report, "src", "data:image/svg+xml");
        filterByTag(report, "svg");
        report.getElementsByTag("a-img").forEach(img -> img.tagName("img"));
        remapImages(report, "src");
        remapImages(report, "href");
        remapImages(report, "srcset");
        return report.outerHtml();
    }
    
    private void filterByTag(Element report, String target) {
        report.getElementsByTag(target).forEach(Element::remove);
    }
    
    private void filterByClass(Element report, String target) {
        report.getElementsByClass(target).forEach(Element::remove);
    }
    
    private void filterByAttributeValue(Element report, String key, String value) {
        report.getElementsByAttributeValue(key, value).forEach(Element::remove);
    }
    
    private void filterByAttributeValueStarting(Element report, String key, String value) {
        report.getElementsByAttributeValueStarting(key, value).forEach(Element::remove);
    }
    
    private void remapImages(Element report, String attribute) {
        report.getElementsByAttribute(attribute).forEach(element -> {
            element.attr(attribute, replaceAllUrls(element.attr(attribute)));
        });
    }
    
    private String replaceAllUrls(String str) {
        String[] parts = str.split(" ");
        for(int i = 0; i < parts.length; i++) {
            String s = parts[i];
            System.out.println(s);
            if(s.startsWith("https://") && s.endsWith(".png")) {
                parts[i] = urlRemap.apply(s);
                continue;
            }
            if(s.startsWith("/") && s.endsWith(".png")) {
                parts[i] = urlRemap.apply("https://www.heise.de" + s);
            }
        }
        return String.join(" ", parts);
    }
    
}
