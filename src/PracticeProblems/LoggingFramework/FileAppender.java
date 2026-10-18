package PracticeProblems.LoggingFramework;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;

public class FileAppender implements LogAppender {
    private final PrintWriter writer;

    public FileAppender(String path) {
        try {
            this.writer = new PrintWriter(new FileWriter(path, true), true);   // true = append, true = autoflush
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    // synchronized: one line is written whole before the next thread's line starts.
    // The lock is per appender, so a slow file never blocks the console.
    @Override
    public synchronized void appendLog(LogMessage message) {
        writer.println(message.format());
    }
}
