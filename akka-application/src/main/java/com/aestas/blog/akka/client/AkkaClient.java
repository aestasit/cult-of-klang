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
    private static List<Integer> list = Lists.newArrayList();

    public static void main(String[] args) throws InterruptedException {

        // init round robin list
        for (int i=0;i<60;i++) {
            list.add(i);
        }
        Iterator<Integer> iterator = Iterators.cycle(list);

        List<Future<String>> l1 = new ArrayList<Future<String>>();
        for (int i = 0; i < 120; i++) {
            int id = iterator.next();
            l1.add(testConcurrency("ping - " + id, 2000L, id));

        }

        for (Future<String> f:l1) {
            f.await();
            System.out.println(f.resultOrException().get());
        }

        System.exit(0);
    }

    private static Future<String> testConcurrency(String message, Long time, int _id) {
        MyService actor = remote()
                .typedActorFor(MyService.class, "my-service-" + _id, 60000L, "localhost", 9919);

        return actor.callAndWait(message, time);


    }


}
