package com.aestas.blog.akka.actor;
import akka.actor.TypedActor;
import akka.dispatch.Future;

/**
 * User: lfi
 */
public class MyAkkaService extends TypedActor implements MyService  {

    private Integer testValue = 0;

    private String x1;
    private String x2;
    public MyAkkaService() {

    }

    public MyAkkaService(String id) {
        getContext().setId(id);
    }

    public MyAkkaService(String a, String b) {

        x1 = a;
        x2 = b;
    }
    public Future<String> getServiceData(final String id) {

        testValue++;
        return future("hello :: " + testValue);
    }

    public Future<Integer> getTestValue() {
        return future(testValue);

    }

    public Future<String> getX1() {
        return future(x1);
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
