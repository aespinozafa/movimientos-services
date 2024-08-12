package ec.domain.logger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LoggerUtil {

    private LoggerUtil() {
        // Evitar la instanciación con un constructor privado
    }

    public static void debug(String message) {
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    public static void debug(String message, Throwable t) {
        if (log.isDebugEnabled()) {
            log.debug(message, t);
        }
    }

    public static void info(String message) {
        if (log.isInfoEnabled()) {
            log.info(message);
        }
    }

    public static void info(String message, Throwable t) {
        if (log.isInfoEnabled()) {
            log.info(message, t);
        }
    }

    public static void warn(String message) {
        if (log.isWarnEnabled()) {
            log.warn(message);
        }
    }

    public static void warn(String message, Throwable t) {
        if (log.isWarnEnabled()) {
            log.warn(message, t);
        }
    }

    public static void error(String message) {
        if (log.isErrorEnabled()) {
            log.error(message);
        }
    }

    public static void error(String message, Throwable t) {
        if (log.isErrorEnabled()) {
            log.error(message, t);
        }
    }

    public static void trace(String message) {
        if (log.isTraceEnabled()) {
            log.trace(message);
        }
    }

    public static void trace(String message, Throwable t) {
        if (log.isTraceEnabled()) {
            log.trace(message, t);
        }
    }
}
