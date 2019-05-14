package pl.scalatech.wiremock.adapter.web;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
@Slf4j
class RestApiEventsListener implements ApplicationListener<ApplicationEvent> {
    private static final String LATEST = "/v1/logs";

    private static final String EVENT_WEB_TAG = "event";
    @Autowired
    private MeterRegistry meterRegistry;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.debug("onApplicationEvent : {}", event.toString());
        if (event instanceof ServletRequestHandledEvent) {
            if (((ServletRequestHandledEvent) event)
                    .getRequestUrl()
                    .equals(LATEST)) {
                meterRegistry.counter("v1.logs.hits", EVENT_WEB_TAG, "countRequest")
                             .increment();
            }
        }
    }
}