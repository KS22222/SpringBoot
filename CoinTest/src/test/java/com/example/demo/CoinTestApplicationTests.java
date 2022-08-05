package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.junit.Before;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoinTestApplication.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
class CoinTestApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    //private WebApplicationContext wac;

    @Test
    public void TestSeq() {
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        try
        {
        	this.mockMvc.perform(MockMvcRequestBuilders.post("/api/renew")
	                .contentType(MediaType.APPLICATION_JSON));
        	
        	this.mockMvc.perform(MockMvcRequestBuilders.get("/coins/search/findByCurrency")
    				.accept(MediaType.ALL_VALUE)
    				.param("currency", "USD")
    				.contentType(MediaType.APPLICATION_JSON)
                    );
        	
        	this.mockMvc.perform(MockMvcRequestBuilders.post("/api/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "  \"currency\": \"NTD\",\n" +
                            "  \"dollar\": \"NTD\",\n" +
                            "  \"ctname\": \"台幣\",\n" +
                            "  \"exrate\": 19439.4789,\n" +
                            "  \"lasttime\": \"2022-08-05 14:34:00\",\n" +
                            "}")
                    .accept(MediaType.APPLICATION_JSON)
                    );
        	
        	this.mockMvc.perform(MockMvcRequestBuilders.put("/api/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "  \"currency\": \"NTD\",\n" +
                            "  \"dollar\": \"NTD\",\n" +
                            "  \"ctname\": \"台幣\",\n" +
                            "  \"exrate\": 9.9,\n" +
                            "  \"lasttime\": \"2022-08-05 14:34:00\",\n" +
                            "}")
                    .accept(MediaType.APPLICATION_JSON)
                    );
        	
        	this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete")
                    .contentType(MediaType.ALL_VALUE)
                    .param("currency", "NTD")
                    .accept(MediaType.ALL_VALUE)
                    );
        	
        	this.mockMvc.perform(MockMvcRequestBuilders.get("api/coindesk")
    				.accept(MediaType.ALL_VALUE)
    				.contentType(MediaType.ALL_VALUE)
                    );
        	
        	this.mockMvc.perform(MockMvcRequestBuilders.post("/api/renew")
	                .contentType(MediaType.APPLICATION_JSON));
        }
        catch (Exception e)
        {
        	System.out.println(e.toString());
        }
    }
}
