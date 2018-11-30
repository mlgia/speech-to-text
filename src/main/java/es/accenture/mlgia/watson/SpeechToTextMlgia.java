package es.accenture.mlgia.watson;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpeechToTextMlgia {

	@Value("${watson.speech.to.text.username}")
	@Getter
	private String usuario;

	@Value("${watson.speech.to.text.password}")
	@Getter
	private String password;
	
	@Value("${language.model}")
	private String languageModel;

	SpeechToText speechService;

	public String getText(File audioFile) {
		String salida = "";

		speechService = new SpeechToText();
		speechService.setUsernameAndPassword(usuario, password);
		
		SpeechResults result = speechService.recognize(audioFile, getRecognizeOptions()).execute();
		log.debug("Texto:" + result.getResults().get(0).getAlternatives().get(0).getTranscript());
		salida = result.getResults().get(0).getAlternatives().get(0).getTranscript();
		
		return salida;
	}

	private RecognizeOptions getRecognizeOptions() {
		return new RecognizeOptions.Builder()
				.continuous(true)
				.model(languageModel)
				.interimResults(true)
				.inactivityTimeout(2000)
				.build();
	}
}
