package dev.tr7zw.heisser.server;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.tr7zw.heisse.api.ArticleAPI;
import dev.tr7zw.heisse.api.UrlUtil;

@ApplicationScoped
@Path("/article/{id}")
public class ArticleProvider {

    private ArticleAPI articleAPI;
    
    @Inject
    public ArticleProvider(ImageProxy proxy) {
        articleAPI = new ArticleAPI(proxy::registerImage);
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getImage(@PathParam("id") String id) throws IOException {
        return Response.ok(articleAPI.getArticle(UrlUtil.getId(id))).build();
    }
    
}
