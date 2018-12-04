package it.almawave.gatawey.textanalytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.ejb.EJB;
import javax.mail.util.ByteArrayDataSource;
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

import it.almawave.gatawey.asr.ServiceUpload;
import it.almawave.gateway.db.GatewayInternalDb;
import it.almawave.gateway.db.bean.DoRequestBean;
import it.pervoice.audiomabox.commontypes._1.FileType;
import it.pervoice.audiomabox.services.common._1.ClientInfoType;
import it.pervoice.audiomabox.services.upload._1.UploadRequest;
import it.pervoice.audiomabox.services.upload._1.UploadRequest.RemoteFile;
import it.pervoice.audiomabox.services.upload._1.UploadResponse;
import it.pervoice.ws.audiomabox.service.upload._1.UploadFault;
import it.pervoice.ws.audiomabox.service.upload._1.UploadWS;

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
	public String doRequest(DoRequestBean request) throws IOException{
		LOGGER.info("[Service doRequest INVOKED]");
		String response = "";
		try {
			
			//leggi file audio
			File file = new File(request.getPercorsoFileAudio()); 
	        byte[] data = FileUtils.readFileToByteArray(file);
	        ByteArrayDataSource rawData = new ByteArrayDataSource(data,"application/octet-stream");
 		
	        //invocare il servizio uploadService
	        UploadWS service = new ServiceUpload().getService(); 
	        //request
	        UploadRequest uploadRequest = new UploadRequest();
	        //clientInfo
	        ClientInfoType clientInfoType = new ClientInfoType();
	        uploadRequest.setClientInfo(clientInfoType);
	        //file
	        RemoteFile remoteFile = new RemoteFile();
	        FileType fileType = new FileType(); 
	        fileType.setName(file.getName());
	        DataHandler dataHandler =  new DataHandler(rawData);
	        fileType.setData(dataHandler);
	        remoteFile.setFile(fileType);
	        uploadRequest.setRemoteFile(remoteFile);
	        
	        //TODO: da finire il popolamento della request
	        UploadResponse uploadResponse = service.upload(uploadRequest);
	        String id = String.valueOf(uploadResponse.getJobElement().get(0).getJobId());
	        
	        response = gatewayInternalDbEjb.doRequest(request, id);
	        
	        
		}catch (FileNotFoundException e) {
			return "File audio non trovato";
		} catch (UploadFault e) {
			e.printStackTrace();
			return "Errore nella chiata al servizio Upload di PerVoice";
		} finally {
			LOGGER.info("[Service doRequest ENDED]");
		}
		
		return response;
	}
	
	
	@GET
	@Path("getStatus")
	@Consumes({MediaType.APPLICATION_JSON})
	public String getStatus(String idRichiesta) throws IOException{
		LOGGER.info("[Service getStatus INVOKED]");
		String response = "";
		try {
		
			response = gatewayInternalDbEjb.getStatus(idRichiesta);
		
		}catch (Exception e) {
			return "Errore ";
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
		String responseString="Gateway service up and running";
		Response result =Response.status(200).entity(responseString).build();
		return result;
	}
}
