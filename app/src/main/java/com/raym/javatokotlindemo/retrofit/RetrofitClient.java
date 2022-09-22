package com.raym.javatokotlindemo.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

	private static final String BASE_URL = "https://api.github.com/";
	private static Retrofit retrofit = null;

	public static GithubAPIService getGithubAPIService() {
		Retrofit retrofit = getClient(BASE_URL);
		return retrofit.create(GithubAPIService.class);
	}

	public static Retrofit getClient(String baseUrl) {
		if (retrofit==null) {
			retrofit = new Retrofit.Builder()
					.baseUrl(baseUrl)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}
		return retrofit;
	}
}
