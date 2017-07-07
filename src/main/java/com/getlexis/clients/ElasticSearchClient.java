package com.getlexis.clients;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.getlexis.types.Template;
import com.google.gson.Gson;

public class ElasticSearchClient {
    private Client client;
    private WebTarget target;
    private static int id = 5;
	private static final String BASE_URL = "http://localhost:9200/template/";
	private static final String INDEX_TYPE = "external";
	private static final String SEARCH_FILTER_RESULT_PARAMETERS = "_search?pretty&filter_path=took,hits.hits._id,hits.hits._score,hits.hits._source";
	
	public ElasticSearchClient() {
        client = ClientBuilder.newClient();
	}
	
	public Response getAllTemplates() {
        target = client.target(BASE_URL + SEARCH_FILTER_RESULT_PARAMETERS);
		return target.request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON).get();
	}
	
	/*
	 * JSON Body for search query is in form:
	 * 
	 * {
	 *	  "query": {
	 *	    "bool": {
	 *	      "should": [
	 *	        { "match": { "template": "{keyword}" } },
	 *	        { "match": { "template": "{keyword}" } }
	 *	      ]
	 *	    }
	 *	  }
	 *	}
	 * 
	 * The 'bool should' clause specifies a list of queries either of 
	 * which must be true for a document to be considered a match.
	 * 
	 */
	public Response getMatchingTemplates(String[] keywords) {
		target = client.target(BASE_URL + SEARCH_FILTER_RESULT_PARAMETERS);
		String json = createJsonQuery(keywords);
		return target.request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON).post(Entity.json(json));
	}

	private String createJsonQuery(String[] keywords) {
		String queryStart = "{ \"query\": { \"bool\": { \"should\": [";
		StringBuilder jsonQuery = new StringBuilder(queryStart);
		
		for (int i = 0; i < keywords.length; i++) {
			if(i + 1 == keywords.length)
				jsonQuery.append("{ \"match\": { \"template\": \"" + keywords[i] + "\" } }");
			else
				jsonQuery.append("{ \"match\": { \"template\": \"" + keywords[i] + "\" } },");
		}
		
		jsonQuery.append("]}}}");
		
		return jsonQuery.toString();
	}
	

	public Response getTemplate(int id) {
		target = client.target(BASE_URL + INDEX_TYPE + "/" + id);
		return target.request(MediaType.APPLICATION_JSON_TYPE).get();
	}

	public void updateTemplate(int id, String newTemplate) {
		String jsonTemplate = escapeJsonData(newTemplate);
		String validatedJsonTemplate = "{ \"doc\": " + jsonTemplate + "}";
		
		target = client.target(BASE_URL + INDEX_TYPE + "/" + id + "/_update");
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON).post(Entity.json(validatedJsonTemplate));
		
		System.out.println(response.getStatus());
	}

	public void createTemplate(String template) {
		target = client.target(BASE_URL + INDEX_TYPE + "/" + id);
		id++;
		
	    String jsonTemplate = escapeJsonData(template);
	    
	    target.request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON).put(Entity.json(jsonTemplate));
	}

	private String escapeJsonData(String templateContent) {
		Template template = new Template();
		template.setTemplate(templateContent);
		
		Gson gson = new Gson();
	    String json = gson.toJson(template);
		return json;
	}

	public void deleteTemplate(int id) {
		target = client.target(BASE_URL + INDEX_TYPE + "/" + id);
		target.request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON).delete();
	}
}