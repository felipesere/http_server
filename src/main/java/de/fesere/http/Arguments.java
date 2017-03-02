package de.fesere.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Arguments {

  private final Map<String, String> parsedArgs;

  public Arguments(String [] input) {
    parsedArgs = parseArgs(input);
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
      System.out.println("Mandatory parameter ["+key+"] missing");
      System.exit(-1);
    }
  }

  private static Map<String, String> parseArgs(String[] args) {
    if(args.length % 2 != 0) {
      System.out.println("Invalid number of parameters: " + Arrays.deepToString(args));
      System.exit(-1);
    }
    Map<String, String> result = new HashMap<>();
    for (int i = 0; i < args.length; i += 2) {
      result.put(args[i], args[i+1]);
    }
    return result;
  }
}
