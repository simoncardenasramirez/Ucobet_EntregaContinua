package co.edu.uco.UcoBet.generales.crosscutting.tracing.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.edu.uco.UcoBet.generales.crosscutting.tracing.TraceService;

@Service
public class ConsoleTraceServiceImpl implements TraceService {

   
    private static final Logger logger = LoggerFactory.getLogger(ConsoleTraceServiceImpl.class);

    @Override
    public void logInfo(String message) {
        logger.info(message);
    }

    @Override
    public void logError(String message, Throwable exception) {
        logger.error(message, exception);
    }
}
