package com.aestas.blog.akka.actor;

import akka.actor.TypedActor;
import akka.actor.TypedActorFactory;

/**
 * User: lfi
 */
public class App {


    public static void main(String[] args) {

        MyService m = TypedActor.newInstance(MyService.class, new TypedActorFactory() {
            public TypedActor create() {
                return new MyAkkaService("someString", "hello");
            }
        });


        String s = m.getX1().await().resultOrException().get();

        if (s != null) System.out.println(s);
    }

}
