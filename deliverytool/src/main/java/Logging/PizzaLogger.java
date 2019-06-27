package Logging;

import Model.PizzenDB.Pizza;
import org.apache.logging.log4j.LogManager;

public class PizzaLogger implements LoggerContract<Pizza> {
    private static PizzaLogger thislogger;
    private static org.apache.logging.log4j.Logger log4jLogger;

    private PizzaLogger() {

    }

    public static PizzaLogger getInstance() {
        if (thislogger == null) {
            thislogger = new PizzaLogger();
        }
        return thislogger;
    }

    private boolean loggerIsActive() {
        return log4jLogger != null;
    }

    private void createLoggerIfNeccesary() {
        if (!loggerIsActive()) {
            log4jLogger = LogManager.getRootLogger();
        }
    }

    private void info(String kind, Pizza pizza) {
        createLoggerIfNeccesary();
        log4jLogger.info(kind + pizza);
    }

    private void debug(Pizza pizza, ERRORMODE mode, Throwable throwable) {
        String modeS = null;
        switch (mode) {
            case ADD:
                modeS = "adding";
                break;
            case REMOVE:
                modeS = "removing";
                break;
        }
        log4jLogger.debug("Error during" + modeS + pizza, throwable);
    }

    @Override
    public void added(Pizza pizza) {
        info("Added", pizza);
    }

    @Override
    public void removed(Pizza pizza) {
        info("Removed", pizza);
    }

    @Override
    public void error(Pizza pizza, ERRORMODE errormode, Throwable throwable) {
        debug(pizza, errormode, throwable);
    }
}
