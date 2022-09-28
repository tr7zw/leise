package dev.tr7zw.leise.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Feed {
    private String title;
    @Singular
    private List<Entry> entries;
    
    public String getOverview(Function<String, String> urlRemap) {
        Element output = new Element(Tag.valueOf("div"), "");
        output.classNames(Collections.singleton("a-layout"));
        output.attr("style", "max-width:42rem");
        output.appendElement("link").attr("rel", "stylesheet").attr("href", "/css/index");
        output.appendElement("link").attr("rel", "stylesheet").attr("href", "/css/ho");
        Element list = output.appendElement("section").classNames(new HashSet<>(Arrays.asList("grid md:place-content-center gap-6 md:gap-y-12 max-w-3xl mb-12".split(" "))));
        for (Entry ent : entries) {
            Element article = new Element(Tag.valueOf("article"), "");
            article.classNames(new HashSet<>(Arrays.asList("flex", "flex-col", "ho-text")));
            Element link = article.appendElement("a").classNames(new HashSet<>(Arrays.asList("group", "md:flex")))
                    .attr("href", ent.getUrl().replace("http://heise.de/-", "/article/"));
            Element figure = link.appendElement("figure").classNames(new HashSet<>(Arrays.asList("mb-4", "md:mb-0",
                    "md:mr-4", "md:w-2/5", "float-right", "w-[28%]", "ml-4", "md:float-none", "md:ml-0")));
            figure.appendElement("img").attr("src", urlRemap.apply(ent.getPreviewImage()));
            Element content = link.appendElement("div").classNames(Collections.singleton("md:w-3/5"));
            content.appendElement("header").appendElement("h3")
                    .classNames(new HashSet<>(Arrays.asList("flex", "flex-col"))).appendElement("span")
                    .classNames(new HashSet<>(Arrays.asList(
                            "text-lg leading-snug md:text-xl md:leading-snug font-bold max-w-prose group-hover:text-brand-branding dark:group-hover:text-white"
                                    .split(" ")))).text(ent.getTitle());
            content.appendElement("p").classNames(new HashSet<>(Arrays.asList("mt-3", "text-base", "md:block"))).text(ent.getDescription());
            list.appendChild(article);
        }
        return output.outerHtml();
    }

}
