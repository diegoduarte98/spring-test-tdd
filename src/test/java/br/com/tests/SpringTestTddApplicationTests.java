package br.com.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SpringTestTddApplicationTests {

	@Test
	public void contextLoads() {
		assertThat("S").isEqualTo("S");
	}
}
