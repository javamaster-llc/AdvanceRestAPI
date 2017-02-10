package com.eugene.advancerestapi.dataconvert;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

@Provider
@Consumes(MediaType.TEXT_PLAIN)
public class DateMessageBodyRead implements MessageBodyReader<Date> {

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return Date.class.isAssignableFrom(type);
	}

	@Override
	public Date readFrom(Class<Date> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
			
		Calendar requestedDate = Calendar.getInstance();
		
		MyDate myDate = new MyDate();
		myDate.setDate(requestedDate.get(Calendar.DATE));
		myDate.setMonth(requestedDate.get(Calendar.MONTH));
		myDate.setYear(requestedDate.get(Calendar.YEAR));
		
		return type.cast(myDate);
	}
}
