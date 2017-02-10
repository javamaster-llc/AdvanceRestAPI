package com.eugene.advancerestapi.dataconvert;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
//TEST :
// http://localhost:8080/advancerestap/webapi/date/today
@Path("date/{dateString}")
public class DateResource {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
/*	public String getRequestedDate(@PathParam("dateString") String dateString) {
		return "Get " + dateString;
*/		
	public String getRequestedDate(@PathParam("dateString") MyDate myDate) {
	return "Get " + myDate.toString();
	}
}
