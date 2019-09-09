package test;

import commands.UrlDataFetcher;

public class MockUrlDataFetcher implements UrlDataFetcher {

  @Override
  public String getUrlData(String requestUrl) {
    if (requestUrl.equals("validUrl") || requestUrl.equals("validUrl?=test")) {
      return "valid data";
    }
    else {
      return "";
    }
  }
}
