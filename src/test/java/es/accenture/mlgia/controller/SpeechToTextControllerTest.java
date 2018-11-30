package es.accenture.mlgia.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author accenture
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpeechToTextControllerTest {

	private static final String FILE_UPLOAD = "audio.wav";

	private final String UPLOAD_SERVICIO = "/speech-to-text";
	private final String INFO_SERVICIO = "/info";
	private final String TEXTO_ESPERADO = "this program is working fine";

	private final String STATUS_OK = "200";

	@Autowired
	private TestRestTemplate template;

	@Test
	public void testUploadFile()  {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("audiofile", new ClassPathResource(FILE_UPLOAD));

		ResponseEntity<String> result = template.postForEntity(
													UPLOAD_SERVICIO, map,String.class);

		assertEquals(STATUS_OK, result.getStatusCode().toString());
		assertEquals(TEXTO_ESPERADO, result.getBody().toString().trim());

	}

	@Test
	public void testInfo() throws Exception {

		ResponseEntity<String> result = template.getForEntity(
													INFO_SERVICIO, String.class);
		System.out.println(result);
		assertEquals(STATUS_OK, result.getStatusCode().toString());
	}

}
