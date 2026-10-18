package PracticeProblems.LoggingFramework;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogMessage {
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final LogLevel logLevel;
    private final String text;
    private final LocalDateTime timestamp;
    private final String threadName;
    private final Throwable throwable; // nullable

    private LogMessage(Builder b) {
        this.logLevel = b.logLevel;
        this.text = b.text;
        this.timestamp = b.timestamp;
        this.threadName = b.threadName;
        this.throwable = b.throwable;
    }

    public LogLevel getLevel() { return logLevel; }
    public String getText() { return text; }

    // fixed format: timestamp, level, thread, message
    public String format() {
        String line = String.format("%s %-5s [%s] %s", TS.format(timestamp), logLevel, threadName, text);
        return throwable == null ? line : line + " | " + throwable;
    }

    public static Builder builder(LogLevel logLevel , String text) {
        return new Builder(logLevel , text);
    }


    public static class Builder {
        private final LogLevel logLevel; // required
        private final String text; // required
        private LocalDateTime timestamp = LocalDateTime.now();
        private String threadName = Thread.currentThread().getName();
        private Throwable throwable;

        private Builder(LogLevel logLevel , String text) {
            this.logLevel = logLevel;
            this.text = text;
        }

        public Builder threadName(String threadName) {
            this.threadName = threadName;
            return this;
        }

        public Builder throwable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public LogMessage build() {
            return new LogMessage(this);
        }

    }
}
