package it.almawave.gatawey.textanalytics;

import java.io.IOException;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import it.almawave.gateway.GatewayServices;
import it.almawave.gateway.db.bean.DoRequestBean;

@Path("/service")
public class Gateway {
	
	private static final Logger LOGGER = Logger.getLogger(Gateway.class);
	@EJB
	GatewayServices gatewayServicesEjb;
	
	public Gateway() {
		try {
			String gatewayServicesLookupName = "java:global/GatewayPackage/GatewayEjb/GatewayServices!it.almawave.gateway.GatewayServices";
			gatewayServicesEjb = (GatewayServices) InitialContext.doLookup(gatewayServicesLookupName);
		} catch (NamingException e) {
			e.printStackTrace();
		}	
	}
	
	@POST
	@Path("doRequest")
	@Consumes({MediaType.APPLICATION_JSON})
	public String doRequest(DoRequestBean request) throws IOException{
		LOGGER.info("[Service doRequest INVOKED]");
		String response = "";
		try {
			
			response = gatewayServicesEjb.doRequest(request);
			
		}catch (Exception e) {
			e.printStackTrace();
			return "Errore " + e.getMessage();
		}finally {
			LOGGER.info("[Service doRequest ENDED]");
		}
		return response;
	}
	
	
	@GET
	@Path("getStatus")
	@Consumes({MediaType.APPLICATION_JSON})
	public String getStatus(String idDifformita) throws IOException{
		LOGGER.info("[Service getStatus INVOKED]");
		String response = "";
		try {
		
			response = gatewayServicesEjb.getStatus(idDifformita);
		
		}catch (Exception e) {
			e.printStackTrace();
			return "Errore " + e.getMessage();
		}finally {
			LOGGER.info("[Service getStatus ENDED]");
		}
		
		return response;
	}
	
	@GET
	@Path("getResponse")
	@Consumes({MediaType.APPLICATION_JSON})
	public String getResponse(String idRichiesta) throws IOException{
		LOGGER.info("[Service getResponse INVOKED]");
		
		
//		Nota audio trascritta 
//		Tre triplette di classificazione e Ranking associato
//		Concetti
//		Entità

		
		LOGGER.info("[Service getResponse ENDED]");
		
		return "da tornare oggetto json";
	}
	
	@GET
	@Path("test")
	//@Produces(MediaType.TEXT_PLAIN)
	public Response sayPlainTextHello() throws IOException, IllegalStateException, SecurityException, SystemException {

		//gatewayInternalDbEjb.insertRequestStatus();
		//String responseString="Gateway service up and running";
		//gatewayInternalDbEjb.tester();
		String responseString=gatewayServicesEjb.startClassification();
		Response result =Response.status(200).entity(responseString).build();
		return result;
	}
	

}
