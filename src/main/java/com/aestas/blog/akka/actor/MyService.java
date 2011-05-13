package com.aestas.blog.akka.actor;

import akka.dispatch.Future;

/**
 * User: lfi
 */
public interface MyService {

    Future<String> getServiceData(String id);
    Future<Integer> getTestValue();
    Future<String> getX1();

    Future<String> callAndWait(String message, Long wait);

}
