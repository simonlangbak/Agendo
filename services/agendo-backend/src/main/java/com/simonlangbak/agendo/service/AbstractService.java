package com.simonlangbak.agendo.loginservice.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * This method is invoked after dependencies have been injected but before the service is put into action.
     * <br>
     * If any unhandled exception is thrown, this service cannot be put into service leading to shut down of the
     * backend.
     */
    @PostConstruct
    protected void onServiceStarted() {
        log.trace("onServiceStarted");
    }
}
