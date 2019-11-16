package io.example.service.common.tracing;

import io.opentracing.contrib.concurrent.TracedExecutorService;
import io.opentracing.util.GlobalTracer;

import javax.enterprise.inject.Produces;
import java.util.concurrent.ForkJoinPool;

public class TracedExecutorServiceProducer {
  @Produces
  public TracedExecutorService produce() {
    return new TracedExecutorService(ForkJoinPool.commonPool(), GlobalTracer.get());
  }
}
