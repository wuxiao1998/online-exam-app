package edu.sandau.util;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jaxrs.JAXRSContract;

import service.UserService;

public class MyClientFact {
	static MyClientFact inst=new MyClientFact();
	static String base_url = Data.loadServiceUrl();

	public static MyClientFact getInstance()
	{
		return inst;
	}



	public UserService getUserService() {
		UserService client1 = Feign.builder()
				.contract(new JAXRSContract())
				.encoder(new GsonEncoder())
				.decoder(new GsonDecoder())
				.target(UserService.class, base_url);

		return client1;
	}
	public static void setBase_url(String base_url) {
		MyClientFact.base_url = base_url;
	}
}