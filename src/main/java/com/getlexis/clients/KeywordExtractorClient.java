package com.getlexis.clients;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class KeywordExtractorClient {
    private Client client;
    private WebTarget target;

    public KeywordExtractorClient(String targetPath) {
        client = ClientBuilder.newClient();
        target = client.target(targetPath);
    }

    public Response extract(String text, int keywordNum) {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("text", text);
        formData.add("wordnum", Integer.toString(keywordNum));

        return target.request(MediaType.APPLICATION_JSON_TYPE)
                .header("X-Mashape-Key", "OiQTLJXSOWmshLMspPJ9GO19U7Lep1SvIQpjsnX82oHBEeBPZW")
                .accept(MediaType.APPLICATION_JSON).post(Entity.form(formData));
    }
}
