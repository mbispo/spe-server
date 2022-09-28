package br.jus.tjms.pontoeletronico.rest;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ResteasyUtil {
	
	// https://gist.github.com/orip/3635246

	public enum ADAPTER {
		LONG, YYYY_MM_DD, YYYY_MM_DD_HH_MM_SS;
	}

	public static Gson createGson() {

		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		
		

		builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
			@Override
			public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
				return new JsonPrimitive(src.getTime());
			}
		});
		
		
		builder.registerTypeAdapter(byte[].class, new JsonDeserializer<byte[]>() {
			public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				try {
					return Base64.decodeBase64(java.net.URLDecoder.decode(json.getAsString(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
			}
		});
		
		builder.registerTypeAdapter(byte[].class, new JsonSerializer<byte[]>() {

			@Override
			public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
				try {
					return new JsonPrimitive(java.net.URLEncoder.encode(Base64.encodeBase64String(src),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return new JsonPrimitive("");
				}
			}
		});

		
		return builder.create();
		
	}

	public static HttpRequest get(String url) {
		return HttpRequest.get(HttpRequest.encode(url)).accept("application/json");
	}

	public static String get(String url, Map<String, String> parametros) {

		try {

			if (parametros != null) {

				for (String key : parametros.keySet()) {
					String value = parametros.get(key);
					if (url.contains("?")) {
						url += '&' + key + "=" + value;
					} else {
						url += '?' + key + "=" + value;
					}

				}

			}

			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(url);
			Response response = target.request().accept(MediaType.APPLICATION_JSON).get();

			if (response.getStatus() == 200) {
				return response.readEntity(String.class);
			} else {
				throw new WebApplicationException("Retorno do Servidor:" + response.getStatus());
			}

		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

	public static String post(String url, Map<String, Object> parametros) {

		try {

			ResteasyClient client = new ResteasyClientBuilder().build();
			
			ResteasyWebTarget target = client.target(url);
			
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			for (String key : parametros.keySet()) {
				Object value = parametros.get(key);

				if (value == null) {
					continue;
				}

				if (value.getClass().isPrimitive() || value instanceof String || value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Boolean) {
					queryParams.add(key, value.toString());

				} else {
					queryParams.add(key, ResteasyUtil.createGson().toJson(value));
				}
			}

			Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(queryParams));

			if (response.getStatus() == 200) {
				return response.readEntity(String.class);
			} else if (response.getStatus() == 204) {
				return null;
			} else {
				throw new WebApplicationException("Retorno do Servidor:" + response.getStatus());
			}

		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	public static String send(String url, Object object) {

		try {
			
			Gson gson = ResteasyUtil.createGson();
			
	        HttpRequest request = HttpRequest.post(url).contentType("application/json").send(gson.toJson(object));
	        
		    Integer code = request.code();

			if (code == 200) {
				return request.message();
			} else if (code == 204) {
				return null;
			} else {
				throw new WebApplicationException("Retorno do Servidor:" + code);
			}

		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	
	public static <T> String sendObjects(String url, List<T> objects) {

		try {
			
			Gson gson = ResteasyUtil.createGson();
			
	        HttpRequest request = HttpRequest.post(url).contentType("application/json").send(gson.toJson(objects));
	        
		    Integer code = request.code();

			if (code == 200) {
				return request.message();
			} else if (code == 204) {
				return null;
			} else {
				throw new WebApplicationException("Retorno do Servidor:" + code);
			}

		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}	
	

	public static <T> T toObject(String response, Class<T> classz) {
		Gson gson = createGson();
		return gson.fromJson(response, classz);

	}

	public static void main(String[] args) {
		System.out.println(toObject("teste", Date.class));
	}

}
