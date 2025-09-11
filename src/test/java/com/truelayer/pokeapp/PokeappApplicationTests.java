package com.truelayer.pokeapp;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
public abstract class PokeappApplicationTests {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	private CacheManager cacheManager;

	@BeforeEach
	void clearCache() {
		cacheManager.getCache("pokemon").clear();
		cacheManager.getCache("translation").clear();
	}

}