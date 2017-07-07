package com.getlexis.resources;

import com.getlexis.clients.ElasticSearchClient;
import com.getlexis.clients.KeywordExtractorClient;
import com.getlexis.types.Keyword;
import com.google.gson.Gson;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/templates")
public class TemplateResource {
    private static ElasticSearchClient elasticSearch = new ElasticSearchClient();
    private static final String KEYWORD_EXTRACTOR_URL = "https://textanalysis-keyword-extraction-v1.p.mashape.com/keyword-extractor-text";
    private static KeywordExtractorClient keywordExtractor = new KeywordExtractorClient(KEYWORD_EXTRACTOR_URL);
    
    //Used for testing purposes
//    private static List<String> dummyKeywords = new ArrayList<String>() {{
//    	add("404");
//    	add("trial");
//    }};

    @POST
    @Path("/email")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postEmail(String email) {
    	Response keywordsResponse = keywordExtractor.extract(email, 20);    	
    	String responseContents = keywordsResponse.readEntity(String.class);
    	String[] keywords = convertJsonToArray(responseContents);
    	
    	return elasticSearch.getMatchingTemplates(keywords);
    }
    
    private String[] convertJsonToArray(String responseContents) {
    	Gson gson = new Gson();
		Keyword keywordWrapper = gson.fromJson(responseContents, Keyword.class);
    	return keywordWrapper.keywords;
	}

	@GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemplates() {
    	return elasticSearch.getAllTemplates();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemplate(@PathParam("id") int id) {
    	return elasticSearch.getTemplate(id);
    }

	@PUT
    @Path("/update/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updateTemplate(@PathParam("id") int id, String newTemplate) {
        elasticSearch.updateTemplate(id, newTemplate);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.TEXT_PLAIN)
    public void postTemplate(String template) {
    	elasticSearch.createTemplate(template);
    }
    
    @DELETE
    @Path("/delete/{id}")
    public void deleteTemplate(@PathParam("id") int id) {
    	elasticSearch.deleteTemplate(id);
    }
}