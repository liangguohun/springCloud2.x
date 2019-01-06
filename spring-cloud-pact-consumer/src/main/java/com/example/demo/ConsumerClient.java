package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsumerClient {
    private String url;

    public ConsumerClient(String url) {
        this.url = url;
    }

    public Map getAsMap(String path, Map<String, String> reqHeads) throws IOException {
    	Request req = Request.Get(url + path);
    	reqHeads.forEach((k,v)->{
    		req.addHeader(new BasicHeader(k,v));
    	});
    	
    	return jsonToMap(req.execute().returnContent().asString());
    }

	public List getAsList(String path) throws IOException {
		return jsonToList(Request.Get(url + path)
				.addHeader("testreqheader", "testreqheadervalue")
				.execute().returnContent().asString());
	}

    public Map post(String path, String body, ContentType mimeType) throws IOException {
        String respBody = Request.Post(url + path)
                .addHeader("testreqheader", "testreqheadervalue")
                .bodyString(body, mimeType)
                .execute().returnContent().asString();
        return jsonToMap(respBody);
    }
    
    public Map post(String path, String body, Map<String, String> reqHeads, ContentType mimeType) throws IOException {
    	Request req = Request.Post(url + path);
    	reqHeads.forEach((k,v)->{
    		req.addHeader(new BasicHeader(k,v));
    	});
    	String respBody = req.bodyString(body, mimeType).execute().returnContent().asString();
    	return jsonToMap(respBody);
    }
    
    private HashMap jsonToMap(String respBody) throws IOException {
      if (respBody.isEmpty()) {
        return new HashMap();
      }
      return new ObjectMapper().readValue(respBody, HashMap.class);
    }
	
	private List jsonToList(String respBody) throws IOException {
		return new ObjectMapper().readValue(respBody, ArrayList.class);		
	}

    public int options(String path) throws IOException {
        return Request.Options(url + path)
                .addHeader("testreqheader", "testreqheadervalue")
                .execute().returnResponse().getStatusLine().getStatusCode();
    }

    public String postBody(String path, String body, ContentType mimeType) throws IOException {
        return Request.Post(url + path)
            .bodyString(body, mimeType)
            .execute().returnContent().asString();
    }
}
