package Logging;

import Model.PizzenDB.Ingredient;
import org.apache.logging.log4j.LogManager;

public class IngredienceLogger implements LoggerContract<Ingredient> {
    private static IngredienceLogger logger;
    private static org.apache.logging.log4j.Logger log4jLogger;

    private IngredienceLogger() {

    }

    public IngredienceLogger getInstance() {
        if (logger == null) {
            logger = new IngredienceLogger();
        }

        return logger;
    }

    private boolean loggerIsActive() {
        return log4jLogger != null;
    }

    private void createLoggerIfNeccesary() {
        if (!loggerIsActive()) {
            log4jLogger = LogManager.getRootLogger();
        }
    }

    @Override
    public void added(Ingredient ingredient) {
        createLoggerIfNeccesary();
        log4jLogger.info("Added" + ingredient);
    }

    @Override
    public void removed(Ingredient ingredient) {
        log4jLogger.info("Removed " + ingredient);
    }

    @Override
    public void error(Ingredient ingredient, ERRORMODE errormode, Throwable throwable) {
        createLoggerIfNeccesary();
        String modeS = null;
        switch (errormode) {
            case ADD:
                modeS = "adding";
            case REMOVE:
                modeS = "removing";
        }
        log4jLogger.debug("Error while " + modeS + ingredient, throwable);
    }
}
