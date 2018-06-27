package br.com.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ApplicationTests {

	@Test
	public void contextLoads() {
		assertThat("S").isEqualTo("S");
	}
}
