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

    static {
       TypedActorConfiguration tac = new TypedActorConfiguration();
            tac.dispatcher(Dispatchers.newExecutorBasedEventDrivenWorkStealingDispatcher("pooled-dispatcher-")
                            .withNewThreadPoolWithLinkedBlockingQueueWithUnboundedCapacity()
                            .setCorePoolSize(60)
                            .setMaxPoolSize(60)
                            .build());

        for (int i=0;i<60;i++) {
            remote().registerTypedActor("my-service-" + i, TypedActor.newInstance(MyService.class, MyAkkaService.class, tac));
        }

        ActorRef[] actors = registry().actors();
        for (ActorRef ar : actors)
            System.out.println("ACTOR ID: " + ar.getId());
    }
}
