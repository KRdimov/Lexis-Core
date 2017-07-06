package com.getlexis.clients;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ElasticSearchClient {
    private Client client;
    private WebTarget target;
	private static final String BASE_URL = "http://localhost:9200/template/";
	private static final String FIND_ALL_QUERY = "_search?q=*&pretty";
	
	public ElasticSearchClient() {
        client = ClientBuilder.newClient();
	}
	
	public Response getAllTemplates() {
        target = client.target(BASE_URL + FIND_ALL_QUERY);
		return target.request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON).get();
	}
}
