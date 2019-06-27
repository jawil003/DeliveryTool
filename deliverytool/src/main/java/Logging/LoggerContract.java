package Logging;

public interface LoggerContract<T> {


    void added(T t);

    void removed(T t);

    void error(T t, ERRORMODE errormode, Throwable throwable);
}
