package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.*;

import com.example.model.Coin;

import com.example.repository.CoinRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CoinController {
		@Autowired
		private CoinRepository coinRepository;
		
		@ResponseStatus(HttpStatus.OK)
		@GetMapping("/coins")
	    public List<Coin> getAll() {
	        return coinRepository.findAll();
	    }
		public Coin getOne(@PathVariable("currency") String currency) {
	        return coinRepository.findByCurrency(currency);
	    }
		
		@GetMapping("/coindesk")
		public String getCoinDesk()
		{
			String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
			try
			{
				String json = "";
				URL obj = new URL(url);
				HttpURLConnection connection = (HttpURLConnection)obj.openConnection();
		        connection.setDoOutput(true);
		        connection.setInstanceFollowRedirects(false);
		        connection.setRequestMethod("GET");
		        connection.setRequestProperty("Content-Type", "application/json");
		        connection.setRequestProperty("charset", "utf-8");
		        connection.connect();
		        InputStream inStream = connection.getInputStream();
		        json = new Scanner(inStream, "UTF-8").useDelimiter("\\Z").next(); // input stream to string
		        return json;
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
			return "";
		}
		
		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping(path="/add")
	    public Coin create(@RequestBody Coin cIn)
	    {
	        coinRepository.save(cIn);
	        return cIn;
	    }
		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping(path="/renew")
		public List<Coin> renewFromcoindesk() {			
	    	// read json and write to db
			coinRepository.deleteAll();
			ObjectMapper mapper = new ObjectMapper();
			String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
			try
			{				
				String dateTime = "";

				JsonNode root = mapper.readValue(new URL(url), JsonNode.class);
				if(root.get("time").isEmpty() == false)
				{
					if(root.get("time").get("updatedISO").asText().isEmpty() == false)
					{
						dateTime = root.get("time").get("updatedISO").asText();
					}
				}
				
				if(root.get("bpi").isEmpty() == false)
				{
					Map<String, Double> dol = new HashMap<>();
				    Iterator<JsonNode> idNodes = root.get("bpi").elements();
				    while (idNodes.hasNext())
				    {
				    	JsonNode c = idNodes.next();
				    	dol.put(c.get("code").asText(), c.get("rate_float").asDouble());
				    }
				    
				    for (Map.Entry<String, Double> entry : dol.entrySet())
				    {				    	
				    	Coin c = new Coin();
					    c.setCurrency(entry.getKey());
					    String CTname = "";
					    switch(entry.getKey())
					    {
						    case "USD":
						    	CTname = "美金";
						    	break;
						    case "GBP":
						    	CTname = "英鎊";
						    	break;
						    case "EUR":
						    	CTname = "歐元";
						    	break;
					    }
					    c.setCtname(CTname);
					    c.setDollar(entry.getKey());
					    c.setExrate(entry.getValue());
					    try
					    {
					    	OffsetDateTime date = null;
						    date = OffsetDateTime.parse(dateTime);
						    c.setLasttime(Timestamp.valueOf(date.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()));
					    }
					    catch (Exception e) 
					    {
					        System.out.printf(e.toString());
					    }
					    coinRepository.save(c);
					}				    
				}
			} 
			catch (IOException e)
			{
				System.out.println(e.getMessage());
			}
	        return coinRepository.findAll();
	    }
		
		@PutMapping(path="/update")
		public Coin updateCoin(@RequestBody Coin cIn)
	    {
	        Coin c = coinRepository.findByCurrency(cIn.getCurrency());
	        if(c == null)
	        {
	        	return null;
	        }
	        c.setCtname(cIn.getCtname());
	        c.setExrate(cIn.getExrate());
	        c.setLasttime(cIn.getLasttime());
	        coinRepository.save(c);
	        return c;
	    }
		
		@DeleteMapping(path="/delete")
		public int deleteByCurrency(@RequestParam String currency)
		{
	    	return coinRepository.deleteByCurrency(currency);
		}

}
