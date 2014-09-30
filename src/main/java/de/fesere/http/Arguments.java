package de.fesere.http;

import java.util.HashMap;
import java.util.Map;

public class Arguments {

  private final Map<String, String> parsedArgs;

  public Arguments(String [] intput) {
    parsedArgs = parseArgs(intput);
  }


  public String getString(String key) {
    exists(key);
    return parsedArgs.get(key);
  }

  public int getInteger(String key) {
    exists(key);
    return Integer.parseInt(parsedArgs.get(key));
  }

  private void exists(String key) {
    if(!parsedArgs.containsKey(key)) {
      throw new RuntimeException("Option ["+key+"] does not exist");
    }
  }

  private static Map<String, String> parseArgs(String[] args) {
    Map<String, String> result = new HashMap<>();
    for (int i = 0; i < args.length; i += 2) {
      result.put(args[i], args[i+1]);
    }
    return result;
  }
}
