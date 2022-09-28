package dev.tr7zw.leise.server;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.tr7zw.leise.api.ArticleAPI;
import dev.tr7zw.leise.api.UrlUtil;

@ApplicationScoped
@Path("/")
public class ArticleProvider {

    private ArticleAPI articleAPI;
    
    @Inject
    public ArticleProvider(ImageProxy proxy) {
        articleAPI = new ArticleAPI(proxy::registerImage);
    }
    
    public Response getContent(String id) throws IOException {
        return Response.ok(articleAPI.getArticle(UrlUtil.getId(id), "?seite=all")).build();
    }
    
    @Path("/article/{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getArticle(@PathParam("id") String id) throws IOException {
        return getContent(id);
    }
    
    @Path("/news/{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getNews(@PathParam("id") String id) throws IOException {
        return getContent(id);
    }
    
    @Path("/meinung/{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getMeinung(@PathParam("id") String id) throws IOException {
        return getContent(id);
    }
    
    @Path("/tests/{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getTest(@PathParam("id") String id) throws IOException {
        return getContent(id);
    }
    
}
