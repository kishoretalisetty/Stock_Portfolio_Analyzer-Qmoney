
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {

  private static String token="076d21137d5269064524cd8effa18fbdcdd9d86a";
  

  private RestTemplate restTemplate;

  protected TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement getStockQuote method below that was also declared in the interface.

  // Note:
  // 1. You can move the code from PortfolioManagerImpl#getStockQuote inside newly created method.
  // 2. Run the tests using command below and make sure it passes.
  //    ./gradlew test --tests TiingoServiceTest
    
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
  throws JsonProcessingException, StockQuoteServiceException {

    // LocalDate startDate=trade.getPurchaseDate();
     if(!isValidDates(from, to))
        throw new RuntimeException();
 
      String url=buildUri(symbol, from,to);
     
     String json="";
     try{
     json=restTemplate.getForObject(url,  String.class);
     }catch(NullPointerException e){
      throw new StockQuoteServiceException(e.getMessage());
     }catch(Exception e){
      throw new StockQuoteServiceException(e.getMessage());
     }
     ObjectMapper objectMapper=new ObjectMapper();
     objectMapper.registerModule(new JavaTimeModule());
     TiingoCandle[] tiingoCandleArray=objectMapper.readValue(json, TiingoCandle[].class);

    //  if(candles==null){
    //   throw new StockQuoteServiceException(new RuntimeException().getMessage());
    //  }
     
     if(tiingoCandleArray==null){
      throw new StockQuoteServiceException(new RuntimeException().getMessage());
     }

      List<Candle> candlesList=Arrays.asList(tiingoCandleArray);
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
    String uriTemplate = "https:api.tiingo.com/tiingo/daily/"+symbol+"/prices?"
         + "startDate="+startDate+"&endDate="+endDate+"&token="+token;
         return uriTemplate;
}






  // TODO: CRIO_TASK_MODULE_EXCEPTIONS
  //  1. Update the method signature to match the signature change in the interface.
  //     Start throwing new StockQuoteServiceException when you get some invalid response from
  //     Tiingo, or if Tiingo returns empty results for whatever reason, or you encounter
  //     a runtime exception during Json parsing.
  //  2. Make sure that the exception propagates all the way from
  //     PortfolioManager#calculateAnnualisedReturns so that the external user's of our API
  //     are able to explicitly handle this exception upfront.

  //CHECKSTYLE:OFF


}
