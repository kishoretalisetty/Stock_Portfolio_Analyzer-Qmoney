
package com.crio.warmup.stock.quotes;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;
import com.crio.warmup.stock.dto.AlphavantageCandle;
import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;

import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class AlphavantageService implements StockQuotesService {

  private RestTemplate restTemplate;
  private String token="SYYNQRCQ4KH0WREZ";
  public  AlphavantageService(RestTemplate restTemplate){
    this.restTemplate=restTemplate;
  }
    
  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement the StockQuoteService interface as per the contracts. Call Alphavantage service
  //  to fetch daily adjusted data for last 20 years.
  //  Refer to documentation here: https://www.alphavantage.co/documentation/
  //  --
  //  The implementation of this functions will be doing following tasks:
  //    1. Build the appropriate url to communicate with third-party.
  //       The url should consider startDate and endDate if it is supported by the provider.
  //    2. Perform third-party communication with the url prepared in step#1
  //    3. Map the response and convert the same to List<Candle>
  //    4. If the provider does not support startDate and endDate, then the implementation
  //       should also filter the dates based on startDate and endDate. Make sure that
  //       result contains the records for for startDate and endDate after filtering.
  //    5. Return a sorted List<Candle> sorted ascending based on Candle#getDate
  //  IMP: Do remember to write readable and maintainable code, There will be few functions like
  //    Checking if given date falls within provided date range, etc.
  //    Make sure that you write Unit tests for all such functions.
  //  Note:
  //  1. Make sure you use {RestTemplate#getForObject(URI, String)} else the test will fail.
  //  2. Run the tests using command below and make sure it passes:
  //    ./gradlew test --tests AlphavantageServiceTest
  //CHECKSTYLE:OFF
    //CHECKSTYLE:ON
  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  1. Write a method to create appropriate url to call Alphavantage service. The method should
  //     be using configurations provided in the {@link @application.properties}.
  //  2. Use this method in #getStockQuote.

 

public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
  throws JsonProcessingException, StockQuoteServiceException {

     if(!isValidDates(from, to))
        throw new RuntimeException();
 
      String url=buildUri(symbol, from,to);
    // System.out.println(url);
     
    
     String response="";
     try{
     response=restTemplate.getForObject(url, String.class);
      }catch(NullPointerException e){
        System.out.println(e.getMessage());
       throw new StockQuoteServiceException(e.getMessage());
      }catch(RuntimeException e){
        System.out.println(e.getMessage());
       throw new StockQuoteServiceException(e.getMessage());
      }
     
      ObjectMapper objectMapper=new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
     
     
      AlphavantageDailyResponse data=objectMapper.readValue(response,  AlphavantageDailyResponse.class);
      Map<LocalDate, AlphavantageCandle> candles=data.getCandles();
      
     if(candles==null){
      throw new StockQuoteServiceException(new RuntimeException().getMessage());
     }
     List<Candle> candlesList=new ArrayList<>();
    
     for( Map.Entry<LocalDate, AlphavantageCandle> entry: candles.entrySet()){
      if(entry.getKey().isAfter(from) && entry.getKey().isBefore(to)){
         entry.getValue().setDate(entry.getKey());
        candlesList.add(entry.getValue());
      }else if(from.equals(entry.getKey()) || to.equals(entry.getKey())){
        entry.getValue().setDate(entry.getKey());
        candlesList.add(entry.getValue());
      }
     }
     
   
        Collections.sort(candlesList, (a,b)->a.getDate().compareTo(b.getDate()));
    
     return candlesList;
   
   }


  
  public  boolean isValidDates(LocalDate startDate, LocalDate endDate){ 
    if(startDate.isBefore(endDate))
    return true;
    else
    return false;
   }


  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Write a method to create appropriate url to call the Tiingo API.
  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    String uriTemplate = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
         +symbol+"&outputsize=full&apikey="+token;
         return uriTemplate;
}


}


// class MyComaparator implements Comparator{
 
//   public int compare(Object obj1, Object obj2){
//     AlphavantageCandle a1=(AlphavantageCandle)obj1;
//     AlphavantageCandle a2=(AlphavantageCandle)obj2;
//     return a1.getDate().compareTo(a2.getDate());
//   }
// }
  // TODO: CRIO_TASK_MODULE_EXCEPTIONS
  //   1. Update the method signature to match the signature change in the interface.
  //   2. Start throwing new StockQuoteServiceException when you get some invalid response from
  //      Alphavantage, or you encounter a runtime exception during Json parsing.
  //   3. Make sure that the exception propagates all the way from PortfolioManager, so that the
  //      external user's of our API are able to explicitly handle this exception upfront.
  //CHECKSTYLE:OFF



