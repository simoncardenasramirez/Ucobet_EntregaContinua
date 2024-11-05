package co.edu.uco.UcoBet.generales.crosscutting.tracing;


public interface TraceService {
    void logInfo(String message);
    void logError(String message, Throwable exception);
}