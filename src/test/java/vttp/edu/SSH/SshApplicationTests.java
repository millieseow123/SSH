package vttp.edu.SSH;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp.edu.SSH.service.SearchService;

@SpringBootTest
class SshApplicationTests {

	@Autowired
	private SearchService searchSvc;

	@Test
	void shouldLoad10Images() throws IOException {
		List<String> gif = searchSvc.getGiphs("dog");
		assertEquals(10, gif.size(), "default number of gifs");
	}

}
