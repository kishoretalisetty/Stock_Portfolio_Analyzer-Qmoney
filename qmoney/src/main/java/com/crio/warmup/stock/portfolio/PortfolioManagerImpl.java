
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;
import java.io.IOException;
import java.net.URISyntaxException;
import com.crio.warmup.stock.PortfolioManagerApplication;
import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {

  

  private static String token="076d21137d5269064524cd8effa18fbdcdd9d86a";
   
  private RestTemplate restTemplate;

  private StockQuotesService stockQuotesService;

  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  protected PortfolioManagerImpl(StockQuotesService stockQuotesService) {
    this.stockQuotesService = stockQuotesService;
  }
  

  //TODO: CRIO_TASK_MODULE_REFACTOR
  // 1. Now we want to convert our code into a module, so we will not call it from main anymore.
  //    Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  //    into #calculateAnnualizedReturn function here and ensure it follows the method signature.
  // 2. Logic to read Json file and convert them into Objects will not be required further as our
  //    clients will take care of it, going forward.

  // Note:
  // Make sure to exercise the tests inside PortfolioManagerTest using command below:
  // ./gradlew test --tests PortfolioManagerTest

  //CHECKSTYLE:OFF
 
  public  List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> tradesFromJsonList,
      LocalDate endDate) throws StockQuoteServiceException{
        
        List<AnnualizedReturn> annualizedReturns=new ArrayList<>();
            
        for(PortfolioTrade trade:tradesFromJsonList){
          List<Candle> candlesList;
          try {
            candlesList = getStockQuote(trade.getSymbol(),trade.getPurchaseDate(),endDate);
            Double buyPrice=getOpeningPriceOnStartDate(candlesList);
         Double sellPrice=getClosingPriceOnEndDate(candlesList);
         AnnualizedReturn ans=calculateAnnualizedReturns(endDate, trade, buyPrice, sellPrice);
         annualizedReturns.add(ans);
          } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            throw new StockQuoteServiceException(e.getMessage());
            
          }
        }
        Collections.sort(annualizedReturns,getComparator());
        return annualizedReturns;
      }

      public  AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {
        double total_returns=(sellPrice-buyPrice)/buyPrice;
        long diffInDays = ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate);
        double total_num_years=(double)diffInDays/365.24;
        double pow=(1/total_num_years);
        double annualized_returns=Math.pow(1+total_returns, pow)-1;
      return new AnnualizedReturn(trade.getSymbol(), annualized_returns, total_returns);
      }


   



  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.


  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
  throws JsonProcessingException, StockQuoteServiceException {

    // LocalDate startDate=trade.getPurchaseDate();
    //  if(!isValidDates(from, to))
    //     throw new RuntimeException();
 
    // // RestTemplate restTemplate=new RestTemplate();
    //  //TiingoCandle start=null;
    //  String url=buildUri(symbol, from,to);
    //  TiingoCandle tiingoCandleArray[]=restTemplate.getForObject(url,  TiingoCandle[].class);
    //  if(tiingoCandleArray==null){
    //   return new ArrayList<>();
    //  } 
   return  stockQuotesService.getStockQuote(symbol, from, to);
    //  List<Candle> candlesList=Arrays.asList(tiingoCandleArray);
      //return candlesList;
   }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
       String uriTemplate = "https:api.tiingo.com/tiingo/daily/"+symbol+"/prices?"
            + "startDate="+startDate+"&endDate="+endDate+"&token="+token;
            return uriTemplate;
  }

  
  public  boolean isValidDates(LocalDate startDate, LocalDate endDate){ 
    if(startDate.isBefore(endDate))
    return true;
    else
    return false;
   }

  public  Double getOpeningPriceOnStartDate(List<Candle> candles) {

    return candles.get(0).getOpen();
  }


  public  Double getClosingPriceOnEndDate(List<Candle> candles) {
    return candles.get(candles.size()-1).getClose();
  }

 







  // private Comparator<AnnualizedReturn> getComparator() {
  //   return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  // }




}
