package com.aestas.blog.akka.actor;

import akka.dispatch.Future;

/**
 * User: lfi
 */
public interface MyService {

    Future<String> callAndWait(String message, Long wait);

}
