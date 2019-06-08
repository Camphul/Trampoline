package com.lucadev.example.trampoline;

import com.lucadev.trampoline.assetstore.configuration.AssetStoreConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AssetStoreConfiguration.class})
public class ExampleAppApplicationTests {

	@Test
	public void contextLoads() {
	}

}
