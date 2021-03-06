package com.example.hackathon_summer.handler;


import com.example.hackathon_summer.domain.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalExeptionHandler {
	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<Response> ServerErrorReturn(HttpServerErrorException e) {
		Response data = new Response();
		data.setStatus(e.getStatusCode());
		data.setMassage(e.getMessage());

		return new ResponseEntity<Response>(data, e.getStatusCode());
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<Response> ClientErrorReturn(HttpClientErrorException e) {
		Response data = new Response();
		data.setStatus(e.getStatusCode());
		data.setMassage(e.getMessage());

		return new ResponseEntity<Response>(data, e.getStatusCode());
	}
}
