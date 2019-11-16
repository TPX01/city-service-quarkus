package io.example.service.common.tracing;

import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;
import lombok.Getter;

public class Trace {

    @Getter
    private String traceId;

    @Getter
    private String spanId;

    @Getter
    private String parentSpanId;

    private Trace(Span activeSpan) {
        if (activeSpan != null) {
            String[] parts = activeSpan.toString().split(":");
            if (parts.length >= 3) {
                traceId = parts[0];
                spanId = parts[1];
                parentSpanId = parts[2];
            }
        }
    }

    public static Trace get() {
        return new Trace(GlobalTracer.get().activeSpan());
    }
}
