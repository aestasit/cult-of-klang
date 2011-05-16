package com.aestas.blog.akka.actor;
import akka.actor.TypedActor;
import akka.dispatch.Future;

/**
 * User: lfi
 */
public class MyAkkaService extends TypedActor implements MyService  {

    public MyAkkaService() {

    }

    public MyAkkaService(String id) {
        getContext().setId(id);
    }


    public Future<String> callAndWait(final String message, final Long wait) {
        System.out.println("bling: " + System.currentTimeMillis() + " | " + message) ;
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("returning: " + message);
        return future("x: " + message);  //To change body of implemented methods use File | Settings | File Templates.
    }


}
