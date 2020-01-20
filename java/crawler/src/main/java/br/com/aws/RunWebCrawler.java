package br.com.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RunWebCrawler implements RequestHandler<String, String>{
  public String handleRequest(String myCount, Context context) {
    return myCount;
  }
}