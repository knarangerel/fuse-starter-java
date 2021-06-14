package org.galatea.starter.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
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
   * Valid range values documentation: https://iexcloud.io/docs/api/#historical-prices.
   */
  private final List<String> validRangeValues = Arrays.asList("max", "5y", "2y", "1y", "ytd", "6m",
      "3m", "1m", "1mm", "5d", "5dm", "date", "dynamic");

  private boolean rangeIsValid(final String range) {
    return validRangeValues.contains(range.toLowerCase());
  }

  private final DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;

  private boolean dateIsValid(final String date) {
    try {
      LocalDate.parse(date, dateFormatter);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

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
   * @param range specified time range of the request.
   * @param date specified date in the format YYYYMMDD.
   * @return a list of historical price data for the given symbol.
   */
  public List<IexHistoricalPrice> getHistoricalPrice(final String symbol, final String range,
      final String date) {
    log.info("Retrieving historical price with symbol {}, range {}, date {}", symbol, range, date);
    boolean isAllLetters = Pattern.matches("[a-zA-Z]+", symbol);
    if (!symbol.isEmpty() && isAllLetters) {
      if (date == null) {
        if (range == null) {
          return iexCloudClient.getHistoricalPriceForSymbol(symbol);
        } else if (rangeIsValid(range)) {
          return iexCloudClient.getHistoricalPriceByRange(symbol, range);
        }
      } else if (dateIsValid(date)) {
        if (range == null) {
          return iexCloudClient.getHistoricalPriceByDate(symbol, date);
        } else if (rangeIsValid(range)) {
          return iexCloudClient.getHistoricalPriceByRangeDate(symbol, range, date);
        }
      }
    }
    return Collections.emptyList();
  }

}
