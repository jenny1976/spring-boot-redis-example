package com.upday.newsapi;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class ArticlesControllerIT {

    @Value("${local.server.port}")
    private String port;

	private URL base;
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
            this.base = new URL("http://localhost:" + port + "/");
            template = new TestRestTemplate();
	}

	@Test
//        @Ignore
	public void getHello() throws Exception {
            ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
            assertThat(response.getBody(), equalTo("Service up and running!"));
	}
}
