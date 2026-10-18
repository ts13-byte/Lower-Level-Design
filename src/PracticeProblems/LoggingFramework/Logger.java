package PracticeProblems.LoggingFramework;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The main singleton class that will be used throughout the application to log messages.
 */
public class Logger {
    private volatile LogLevel minLevel = LogLevel.INFO;   // one thread sets it, many read it
    private final List<LogAppender> appenders = new CopyOnWriteArrayList<>();

    private Logger() { }

    // Initialization-on-demand holder: the JVM loads Holder only on first getInstance(),
    // and class loading is thread-safe, so no synchronized or volatile is needed for the instance.
    private static class Holder {
        private static final Logger INSTANCE = new Logger();
    }

    public static Logger getInstance() { return Holder.INSTANCE; }

    public void setLevel(LogLevel level) { this.minLevel = level; }
    public void addAppender(LogAppender appender) { appenders.add(appender); }
    public void removeAppender(LogAppender appender) { appenders.remove(appender); }

    public void debug(String text) { log(LogLevel.DEBUG, text, null); }
    public void info(String text)  { log(LogLevel.INFO, text, null); }
    public void warn(String text)  { log(LogLevel.WARN, text, null); }
    public void error(String text) { log(LogLevel.ERROR, text, null); }
    public void error(String text, Throwable t) { log(LogLevel.ERROR, text, t); }

    private void log(LogLevel level, String text, Throwable t) {
        if (!level.isAtLeast(minLevel)) return;   // filter first, so dropped messages cost almost nothing
        LogMessage message = LogMessage.builder(level, text).throwable(t).build();
        for (LogAppender appender : appenders) {
            appender.appendLog(message);
        }
    }

}
