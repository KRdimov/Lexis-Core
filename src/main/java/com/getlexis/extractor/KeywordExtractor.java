package com.getlexis.extractor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class KeywordExtractor {
    private Client client;
    private WebTarget target;

    public KeywordExtractor(String targetPath) {
        client = ClientBuilder.newClient();
        target = client.target(targetPath);
    }

    public Response extract(String text, int keywordNum) {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("text", text);
        formData.add("wordnum", Integer.toString(keywordNum));

        return target.request(MediaType.APPLICATION_JSON_TYPE)
                .header("X-Mashape-Key", "Wjhgehr8UlmshKraL8WKS7syXeSFp1XwkTVjsn0jnhL6sDwE6K")
                .accept(MediaType.APPLICATION_JSON).post(Entity.form(formData));
    }
}
