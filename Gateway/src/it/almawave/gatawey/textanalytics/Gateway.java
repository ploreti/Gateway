package it.almawave.gatawey.textanalytics;

import java.io.File;
import java.io.FileNotFoundException;
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
import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almawave.gatawey.textanalytics.bean.DoRequestINBean;
import it.almawave.gateway.db.GatewayInternalDb;

@Path("/service")
public class Gateway {
	
	private static final Logger LOGGER = Logger.getLogger(Gateway.class);
	@EJB
	GatewayInternalDb gatewayInternalDbEjb;
	
	public Gateway() {
		try {
			String gatewayInternalDbLookupName = "java:global/GatewayPackage/GatewayEjb/GatewayInternalDb!it.almawave.gateway.db.GatewayInternalDb";
			gatewayInternalDbEjb = (GatewayInternalDb) InitialContext.doLookup(gatewayInternalDbLookupName);
		} catch (NamingException e) {
			e.printStackTrace();
		}	
	}
	
	@POST
	@Path("doRequest")
	@Consumes({MediaType.APPLICATION_JSON})
	public String doRequest(DoRequestINBean request) throws IOException{
		LOGGER.info("[Service doRequest INVOKED]");
		try {
			//leggi file audio
			File file = new File(request.getPercorsoFileAudio()); 
	        byte[] data = FileUtils.readFileToByteArray(file);
 		
	        ObjectMapper objectMapper = new ObjectMapper();
		}catch (FileNotFoundException e) {
			return "File audio non trovato";
		} finally {
			LOGGER.info("[Service doRequest ENDED]");
		}
		
		return "Identificativo univoco della richiesta";
	}
	
	
	@GET
	@Path("getStatus")
	@Consumes({MediaType.APPLICATION_JSON})
	public String getStatus(String idRichiesta) throws IOException{
		LOGGER.info("[Service getStatus INVOKED]");
		
//		Stato della richiesta:
//		Ricevuta
//		In attesa di lavorazione
//		In lavorazione
//		Completata
		
		LOGGER.info("[Service getStatus ENDED]");

		return "Ricevuta";
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
		gatewayInternalDbEjb.insertRequestStatus();
		gatewayInternalDbEjb.insertRequest();
		String responseString="Gateway service up and running";
		Response result =Response.status(200).entity(responseString).build();
		return result;
	}
}
