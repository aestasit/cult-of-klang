package com.aestas.blog.akka.client;

import akka.dispatch.Future;
import com.aestas.blog.akka.actor.MyService;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static akka.actor.Actors.remote;

/**
 * User: lfi
 */
public class AkkaClient {

    public static void main(String[] args) throws InterruptedException {

        Iterator<MyService> iterator; {
            List<MyService> list = Lists.newArrayList();

            // init round robin list
            for (int i=0;i<60;i++) list.add(remote().typedActorFor(MyService.class, "my-service-" + i, 60000L, "localhost", 9919));

            iterator = Iterators.cycle(list);
        }


        List<Future<String>> l1 = new ArrayList<Future<String>>();
        for (int i = 0; i < 120; i++)
            l1.add(iterator.next().callAndWait("ping - " + i, 2000L));

        for (Future<String> f : l1)
            System.out.println(f.get());

        System.exit(0);
    }
}
