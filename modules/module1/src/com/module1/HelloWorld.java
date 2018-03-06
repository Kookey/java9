package com.module1;

import com.module2.Person;

import java.util.logging.Logger;

public class HelloWorld {

     private static final Logger LOG = Logger.getLogger("HelloWorld");

     public static void main(String[] args) {
          System.out.println("Hello World!");
          LOG.info("Hello Logger");

          System.out.println(new Person());
     }

}
