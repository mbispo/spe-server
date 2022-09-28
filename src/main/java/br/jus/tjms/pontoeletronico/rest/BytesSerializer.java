package br.jus.tjms.pontoeletronico.rest;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class BytesSerializer extends JsonSerializer<byte[]> {

	@Override
	public void serialize(byte[] bytes, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(java.net.URLEncoder.encode(Base64.encodeBase64String(bytes),"UTF-8"));
	}

}