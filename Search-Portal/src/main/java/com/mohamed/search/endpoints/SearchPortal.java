package com.mohamed.search.endpoints;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;

@RestController
public class SearchPortal {
    final
    RestTemplate restTemplate;

    private static final String GET_MOVIES_ENDPOINT_URL = "http://localhost:9093/getMovies";
    private static final String GET_MOVIE_BY_KEYWORD_URL="http://localhost:9093/findMovieByKeyword/";
    private static final String GET_MOVIE_BY_COUNTRY_URL="http://localhost:9093/getMoviesByCountry/";

    public SearchPortal(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getdefaultResponse")
    @RequestMapping(value = "/search-portal/findAllMovies",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getMovies(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity < String > ("parameters", headers);
        ResponseEntity <String> result = restTemplate.exchange(GET_MOVIES_ENDPOINT_URL, HttpMethod.GET, entity,String.class);
       return new ResponseEntity(result.getBody(),HttpStatus.OK);
    }
    @HystrixCommand(fallbackMethod = "callMovieServiceAndGetData_Fallback")
    @RequestMapping(value = "/search-portal/findMovieByKeyword/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity findMovieByKeyword(@PathVariable("keyword")String keyword){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept",MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity <String> ("parameters", httpHeaders);
        ResponseEntity<String>responseEntity=restTemplate.exchange(GET_MOVIE_BY_KEYWORD_URL+keyword,HttpMethod.GET,entity,String.class);
        return new ResponseEntity(responseEntity.getBody(),HttpStatus.OK);

    }
    @HystrixCommand(fallbackMethod = "callMovieServiceAndGetData_Fallback")
    @RequestMapping(value = "/search-portal/getMoviesByCountry/{country}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getMoviesByCountry(@PathVariable("country")String country){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept",MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity <String> ("parameters", httpHeaders);
        ResponseEntity responseEntity=restTemplate.exchange(GET_MOVIE_BY_COUNTRY_URL+country, HttpMethod.GET, entity, String.class);
        return new ResponseEntity(responseEntity.getBody(),HttpStatus.FOUND);
    }
public ResponseEntity getdefaultResponse(){
        return new ResponseEntity("it can be :\n" +
                "1- Movies-Service could be stopped \n" +
                                         "2- the List of Movie(Database) is Empty",HttpStatus.OK);
}
    private ResponseEntity callMovieServiceAndGetData_Fallback(String param) {

        System.out.println("Movie-Service is down!!! fallback route enabled...\n");

        return new ResponseEntity("CIRCUIT BREAKER ENABLED!!! No Response From Movie-Service at this moment. \n" +
                " Service will be back shortly - " + new Date(),HttpStatus.OK);
    }

}
