package rba.onboarding.writerservice.service;

import org.slf4j.Logger;

public abstract class BaseService {


    protected final Logger log;

    protected BaseService(Logger log) {
        this.log = log;
    }
}
