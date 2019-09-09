package commands;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RealUrlDataFetcher implements UrlDataFetcher {
  public String getUrlData(String requestUrl) { // throws MalformedURLException, IOException
    String out = "";
    try {
      Scanner scanner =
          new Scanner(new URL(requestUrl).openStream(), StandardCharsets.UTF_8.toString());
      scanner.useDelimiter("\\A");
      out = scanner.hasNext() ? scanner.next() : "";
      scanner.close();
    } catch (Exception e) {
    } finally {
    }
    return out;
  }
}
