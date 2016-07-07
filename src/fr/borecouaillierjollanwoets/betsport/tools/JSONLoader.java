package fr.borecouaillierjollanwoets.betsport.tools;

import java.io.BufferedReader;
import java.io.IOException;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	@Nullable
	public static JSONObject load(HttpServletRequest request, HttpServletResponse response, JSONLoaderCallback callback) throws IOException {

		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
        JSONObject jsonObject = null;
        try {
        	BufferedReader reader = request.getReader();
        	while ((line = reader.readLine()) != null) {
        		stringBuffer.append(line);
        	}
        	jsonObject = new JSONObject(stringBuffer.toString());
        	if(callback.run(request, response, jsonObject)) {
        		return jsonObject;
        	}
        } catch (JSONException | IOException ignored) {
    		response.getWriter().append("{\"error\":\"bad_request\"}").close();
        }
        return null;
	}
}
