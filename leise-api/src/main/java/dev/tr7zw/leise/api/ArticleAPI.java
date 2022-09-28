package dev.tr7zw.leise.api;

import java.io.IOException;
import java.util.Collections;
import java.util.function.Function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class ArticleAPI {

    private Function<String, String> urlRemap;
    
    public ArticleAPI(Function<String, String> urlRemap) {
        this.urlRemap = urlRemap;
    }
    
    public String getArticle(int id, String extras) throws IOException {
        Document doc = Jsoup.connect("https://www.heise.de/-" + id).followRedirects(true).get();
        if(extras != null) {
            doc = Jsoup.connect(doc.baseUri() + extras).followRedirects(true).get();
        }
        Element report = doc.getElementById("meldung");
        report.appendElement("link").attr("rel", "stylesheet").attr("href", "/css/index");
        report.appendElement("link").attr("rel", "stylesheet").attr("href", "/css/ho");
        filterByClass(report, "article-layout__header-wrbng");
        filterByClass(report, "article-sidebar");
        filterByClass(report, "a-ad");
        filterByClass(report, "a-article-action--pocket");
        filterByClass(report, "opt-in__content-container");
        filterByClass(report, "comment-button");
        filterByClass(report, "stage-card");
        filterByAttributeValue(report, "name", "meldung.ho.bottom.zurstartseite");
        filterByAttributeValue(report, "name", "meldung.ho.header.kommentarelesen");
        filterByAttributeValue(report, "name", "meldung.ct.header.kommentarelesen");
        filterByAttributeValue(report, "name", "meldung.ho.header.drucken");
        filterByAttributeValue(report, "data-component", "RecommendationBox");
        filterByAttributeValueStarting(report, "src", "data:image/svg+xml");
        filterByTag(report, "svg");
        filterByTag(report, "noscript");
        filterByTag(report, "a-paid-content-teaser");
        report.getElementsByTag("a-img").forEach(img -> img.tagName("img"));
        remapImages(report, "src");
        remapImages(report, "href");
        remapImages(report, "srcset");
        Element output = new Element(Tag.valueOf("div"), "");
        output.classNames(Collections.singleton("a-layout"));
        output.attr("style", "max-width:42rem");
        output.appendChild(report);
        return output.outerHtml();
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
            if(s.startsWith("https://") && (s.contains(".png") || s.contains(".jpeg"))) {
                parts[i] = urlRemap.apply(s);
                continue;
            }
            if(s.startsWith("/") && (s.contains(".png") || s.contains(".jpeg"))) {
                parts[i] = urlRemap.apply("https://www.heise.de" + s);
            }
        }
        return String.join(" ", parts);
    }
    
}
