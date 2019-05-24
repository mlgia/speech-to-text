package es.accenture.mlgia.watson;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpeechToTextMlgia {

	SpeechToText speechService;
	
	@Value("${watson.apikey}")
	@Getter
	private String apikey;
	
	@Value("${watson.endpoint}")
	@Getter
	private String endpoint;

	public String getText(File audioFile) {
		String salida = "";
		
		IamOptions options = new IamOptions.Builder()
			    .apiKey(apikey)
			    .build();
		
		speechService = new SpeechToText(options);
		speechService.setEndPoint(endpoint);
		
		RecognizeOptions recognizeOptions;
		try {
			recognizeOptions = new RecognizeOptions.Builder()
				    .audio(audioFile)
				    .contentType(RecognizeOptions.ContentType.AUDIO_WAV)
				    .interimResults(true)
					.inactivityTimeout(2000)
					.model(RecognizeOptions.Model.ES_ES_BROADBANDMODEL)
				    .maxAlternatives(1)
				    .build();
					
			SpeechRecognitionResults speechRecognitionResults = speechService.recognize(recognizeOptions).execute().getResult();
			
			if (speechRecognitionResults.getResults().size() > 0) {
				if (speechRecognitionResults.getResults().get(0).getAlternatives().size() > 0) {
					salida = speechRecognitionResults.getResults().get(0).getAlternatives().get(0).getTranscript();
				}
			}
			log.debug("Salida:" + salida);
						
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return salida;
	}
}
