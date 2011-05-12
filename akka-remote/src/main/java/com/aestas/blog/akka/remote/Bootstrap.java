package com.aestas.blog.akka.remote;

import akka.actor.ActorRef;
import akka.actor.TypedActor;
import akka.actor.TypedActorConfiguration;
import akka.config.Supervision;
import akka.config.TypedActorConfigurator;
import akka.dispatch.Dispatchers;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.ThreadPoolConfigDispatcherBuilder;
import com.aestas.blog.akka.actor.MyAkkaService;
import com.aestas.blog.akka.actor.MyService;

import static akka.actor.Actors.registry;
import static akka.actor.Actors.remote;

/**
 * User: lfi
 */
public class Bootstrap {

    private static TypedActorConfigurator configurator = new TypedActorConfigurator();
    private final static int TIMEOUT = 5000;

    static {


        MessageDispatcher md = Dispatchers.newExecutorBasedEventDrivenWorkStealingDispatcher("pooled-dispatcher-")
                            .withNewThreadPoolWithLinkedBlockingQueueWithUnboundedCapacity()
                            .setCorePoolSize(60)
                            .setMaxPoolSize(60)
                            .build();

        for (int i=0;i<60;i++) {
            TypedActorConfiguration tac = new TypedActorConfiguration();
            tac.dispatcher(md);

            remote().registerTypedActor("my-service-" + i, TypedActor
                    .newInstance(MyService.class, MyAkkaService.class, tac));
        }

//
        //MyService m = configurator.getInstance(MyService.class);
        //remote().registerTypedActor("my-service-10", TypedActor.newInstance(MyService.class, MyAkkaService.class,cc));

        listActors();

    }

    private static void listActors() {
        ActorRef[] actors = registry().actors();
        for (ActorRef ar : actors) {
            System.out.println("ACTOR ID: " + ar.getId());
            // ar.start(); // is that needed?
        }
    }
}
