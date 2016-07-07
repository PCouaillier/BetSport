package fr.borecouaillierjollanwoets.betsport.tools;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONLoader {
	
	@FunctionalInterface
	public interface JSONLoaderCallback {
		public  boolean run(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject);
	}

	/**
	 * 
	 * @param request from http
	 * @param response to interact with callback
	 * @return JSONObject or null on error 
	 * @throws IOException
	 */
	public static JSONObject load(HttpServletRequest request, HttpServletResponse response, JSONLoaderCallback callback) throws IOException {

		StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        JSONObject jsonObject = null;
        try {
        	BufferedReader reader = request.getReader();
        	while ((line = reader.readLine()) != null) {
        		stringBuffer.append(line);
        	}
        	jsonObject = HTTP.toJSONObject(stringBuffer.toString());
        	if(callback.run(request, response, jsonObject)) {
        		return jsonObject;
        	}
        	return null;
        } catch (JSONException | IOException ignored) {
    		response.getWriter().append("{\"error\":\"bad_request\"}").close();
        	return jsonObject;
        }
	}
}
