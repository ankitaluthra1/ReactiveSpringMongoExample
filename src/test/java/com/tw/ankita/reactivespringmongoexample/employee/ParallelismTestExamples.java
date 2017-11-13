package com.tw.ankita.reactivespringmongoexample.employee;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ParallelismTestExamples {

    @Test
    public void shouldUseParallelThreadsWithStreams(){

        Flux.just("A","B","C").map(s->s.toUpperCase()).subscribeOn(Schedulers.newParallel("1")).subscribe();

    }

}
