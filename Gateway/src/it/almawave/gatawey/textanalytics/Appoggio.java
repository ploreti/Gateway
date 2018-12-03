package it.almawave.gatawey.textanalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.almawave.gatawey.textanalytics.bean.DoRequestINBean;

public class Appoggio {

	public static void main(String[] args) throws JsonProcessingException {
		 ObjectMapper objectMapper = new ObjectMapper();
		 DoRequestINBean request = new DoRequestINBean();
		 request.setPercorsoFileAudio("C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3");
		 request.setIdDifformita("1");
		 request.setDtp("dtp");
		 request.setSpecializzazione("specializzazione");
		 request.setTipoVisita("tipoVisita");
		 
		String json = objectMapper.writeValueAsString(request);
		System.out.println(json);

	}

}
