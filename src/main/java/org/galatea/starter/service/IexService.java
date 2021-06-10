package org.galatea.starter.service;

import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * A layer for transformation, aggregation, and business required when retrieving data from IEX.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IexService {

  @NonNull
  private IexClient iexClient;

  @NonNull
  private IexCloudClient iexCloudClient;

  /**
   * Get all stock symbols from IEX.
   *
   * @return a list of all Stock Symbols from IEX.
   */
  public List<IexSymbol> getAllSymbols() {
    return iexClient.getAllSymbols();
  }

  /**
   * Get the last traded price for each Symbol that is passed in.
   *
   * @param symbols the list of symbols to get a last traded price for.
   * @return a list of last traded price objects for each Symbol that is passed in.
   */
  public List<IexLastTradedPrice> getLastTradedPriceForSymbols(final List<String> symbols) {
    if (CollectionUtils.isEmpty(symbols)) {
      return Collections.emptyList();
    } else {
      return iexClient.getLastTradedPriceForSymbols(symbols.toArray(new String[0]));
    }
  }

  /**
   * Get the historical price data for a given stock symbol.
   *
   * @param symbol stock symbol to get historical data for.
   * @return a list of historical price data for the given symbol.
   */
  public List<IexHistoricalPrice> getHistoricalPriceForSymbol(final String symbol) {
    log.info("Retrieving historical price data with symbol {}", symbol);
    return iexCloudClient.getHistoricalPriceForSymbol(symbol);
  }

  /**
   * Get the historical price data for a given stock symbol.
   *
   * @param symbol stock symbol to get historical data for.
   * @param range specified time range of the request.
   * @return a list of historical price data for the given symbol and range.
   */
  public List<IexHistoricalPrice> getHistoricalPriceByRange(final String symbol,
      final String range) {
    log.info("Retrieving historical price data with symbol {}, range{}", symbol, range);
    return iexCloudClient.getHistoricalPriceByRange(symbol, range);
  }

  /**
   * Get the historical price data for a given stock symbol.
   *
   * @param symbol stock symbol to get historical data for.
   * @param date specified date in the format YYYYMMDD.
   * @return a list of historical price data for the given symbol, range, and date.
   */
  public List<IexHistoricalPrice> getHistoricalPriceByDate(final String symbol, final String date) {
    log.info("Retrieving historical price data with symbol {}, date {}", symbol, date);
    return iexCloudClient.getHistoricalPriceByDate(symbol, date);
  }

  /**
   * Get the historical price data for a given stock symbol.
   *
   * @param symbol stock symbol to get historical data for.
   * @param range specified time range of the request.
   * @param date specified date in the format YYYYMMDD.
   * @return a list of historical price data for the given symbol, range, and date.
   */
  public List<IexHistoricalPrice> getHistoricalPriceByDate(final String symbol, final String range,
      final String date) {
    log.info("Retrieving historical price data with symbol {}, range {}, date {}",
        symbol, range, date);
    return iexCloudClient.getHistoricalPriceByDate(symbol, range, date);
  }

}
