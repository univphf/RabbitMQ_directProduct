package com.ht.dev.directP;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.Scanner;

public class EmitLogDirect {

  private static final String EXCHANGE_NAME = "direct_logs";

  public static void main(String[] argv) throws Exception
  {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
      try (Connection connection = factory.newConnection()) {
          Channel channel = connection.createChannel();

 channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
 channel.confirmSelect();

          //String color = getColor(argv);
          //String message = getMessage(argv);
          String color;
          String message;

         Scanner sc=new Scanner(System.in);
         boolean fin=false;
         while (!fin)
         {
         System.out.println("Couleur : ");color=sc.nextLine();
          System.out.println("Message : ");message=sc.nextLine();

          channel.basicPublish(EXCHANGE_NAME, color, null, message.getBytes("UTF-8"));
          System.out.println(" [x] Envoi de : '" + color + "':'" + message + "'");

         }

          channel.close();
      }
  }

  private static String getColor(String[] strings){
    if (strings.length < 1)
    	    return "blue";
    return strings[0];
  }

  private static String getMessage(String[] strings){
    if (strings.length < 2)
    	    return "Hello World!";
    return joinStrings(strings, " ", 1);
  }

  private static String joinStrings(String[] strings, String delimiter, int startIndex) {
    int length = strings.length;
    if (length == 0 ) return "";
    if (length < startIndex ) return "";
    StringBuilder words = new StringBuilder(strings[startIndex]);
    for (int i = startIndex + 1; i < length; i++) {
        words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }
}

