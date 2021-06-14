package org.galatea.starter.service;

import java.util.List;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * A Feign Declarative REST Client to access endpoints from IEX Cloud to get market data
 * See https://iexcloud.io/docs/api/
 */
@FeignClient(name = "IEXCloud", url = "${spring.rest.iexCloudPath}")
public interface IexCloudClient {

  /**
   * Get the historical price data for a given stock symbol.
   * See https://iexcloud.io/docs/api/#historical-prices.
   *
   * @param symbol stock symbol to get historical data for.
   * @return a list of historical price data.
   */
  @GetMapping("/stock/{symbol}/chart?token=${spring.rest.iexApiToken}")
  List<IexHistoricalPrice> getHistoricalPriceForSymbol(@PathVariable("symbol") String symbol);

  /**
   * Get the historical price data stock for a given symbol with specified range.
   * See https://iexcloud.io/docs/api/#historical-prices.
   *
   * @param symbol stock symbol to get historical data for.
   * @param range specified time range. Options listed in the link above.
   * @return  a list of historical price data with range.
   */
  @GetMapping("/stock/{symbol}/chart/{range}?token=${spring.rest.iexApiToken}")
  List<IexHistoricalPrice> getHistoricalPriceByRange(@PathVariable("symbol") String symbol,
      @PathVariable("range") String range);

  /**
   * Get the historical price data stock for a given symbol with specified range.
   * See https://iexcloud.io/docs/api/#historical-prices.
   *
   * @param symbol stock symbol to get historical data for.
   * @param date specified date in the format YYYYMMDD. More in the link above.
   * @return  a list of historical price data with range.
   */
  @GetMapping("/stock/{symbol}/chart/{date}?token=${spring.rest.iexApiToken}")
  List<IexHistoricalPrice> getHistoricalPriceByDate(@PathVariable("symbol") String symbol,
      @PathVariable("date") String date);

  /**
   * Get the historical price data stock for a given symbol with specified range and date.
   * See https://iexcloud.io/docs/api/#historical-prices.
   *
   * @param symbol stock symbol to get historical data for.
   * @param range specified time range. Options listed in the link above.
   * @param date specified date in the format YYYYMMDD. More in the link above.
   * @return  a list of historical price data on date, possibly with range.
   */
  @GetMapping("/stock/{symbol}/chart/{range}/{date}?token=${spring.rest.iexApiToken}")
  List<IexHistoricalPrice> getHistoricalPriceByRangeDate(@PathVariable("symbol") String symbol,
      @PathVariable("range") String range, @PathVariable("date") String date);

}
