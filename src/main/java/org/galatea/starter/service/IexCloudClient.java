package org.galatea.starter.service;

import java.util.List;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "IEXCloud", url = "${spring.rest.iexCloudPath}")
public interface IexCloudClient {

  @GetMapping("/stock/{symbol}/chart?token=${spring.rest.iexApiToken}")
  List<IexHistoricalPrice> getHistoricalPriceForSymbols(@PathVariable("symbol") String symbol);

  @GetMapping("/stock/{symbol}/chart/{range}?token=${spring.rest.iexApiToken}")
  List<IexHistoricalPrice> getHistoricalPriceWithRange(@PathVariable("symbol") String symbol,
      @PathVariable("range") String range);

  @GetMapping("/stock/{symbol}/chart/{range}/{date}?token=${spring.rest.iexApiToken}")
  List<IexHistoricalPrice> getHistoricalPriceByDate(@PathVariable("symbol") String symbol,
      @PathVariable("range") String range, @PathVariable("date") String date);


  // TODO: comment and documentation
}
