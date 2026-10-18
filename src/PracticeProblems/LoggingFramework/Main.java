package PracticeProblems.LoggingFramework;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class Main {

    private static int failures = 0;

    // A third, custom destination. Six lines, and nothing else in the system had to change.
    static class CollectingAppender implements LogAppender {
        final List<LogMessage> messages = Collections.synchronizedList(new ArrayList<>());
        @Override public void appendLog(LogMessage message) { messages.add(message); }
    }

    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getInstance();
        check("singleton returns the same instance", logger == Logger.getInstance(), "two different instances");

        // ---------- phase 1: filtering, multiple destinations, format ----------
        Path file1 = Files.createTempFile("log-test-1-", ".log");
        file1.toFile().deleteOnExit();

        ConsoleAppender console = new ConsoleAppender();
        CollectingAppender collector = new CollectingAppender();
        FileAppender fileAppender1 = new FileAppender(file1.toString());
        logger.addAppender(console);
        logger.addAppender(collector);
        logger.addAppender(fileAppender1);

        logger.setLevel(LogLevel.WARN);
        logger.debug("debug-1");
        logger.info("info-1");
        logger.warn("warn-1");
        logger.error("error-1");
        check("messages below the minimum level are dropped (only WARN and ERROR kept)",
                collector.messages.size() == 2, "kept " + collector.messages.size());
        check("kept messages are WARN then ERROR",
                collector.messages.get(0).getLevel() == LogLevel.WARN
                        && collector.messages.get(1).getLevel() == LogLevel.ERROR, "wrong levels");

        logger.setLevel(LogLevel.DEBUG);
        logger.debug("debug-2");
        check("lowering the level at runtime lets DEBUG through",
                collector.messages.size() == 3, "kept " + collector.messages.size());

        logger.error("boom", new IllegalStateException("bad state"));
        check("fourth message collected", collector.messages.size() == 4, "kept " + collector.messages.size());

        List<String> lines1 = Files.readAllLines(file1);
        check("file got the same number of messages as the collector",
                lines1.size() == collector.messages.size(),
                "file " + lines1.size() + " vs collector " + collector.messages.size());
        check("file line has level, thread and text",
                lines1.get(0).contains("WARN") && lines1.get(0).contains("[main]") && lines1.get(0).contains("warn-1"),
                "line was: " + lines1.get(0));
        check("exception is appended to the line",
                lines1.get(3).contains("boom") && lines1.get(3).contains("IllegalStateException: bad state"),
                "line was: " + lines1.get(3));

        // ---------- phase 2: concurrency, with the file as the only destination ----------
        logger.removeAppender(console);
        logger.removeAppender(collector);
        logger.removeAppender(fileAppender1);

        Path file2 = Files.createTempFile("log-test-2-", ".log");
        file2.toFile().deleteOnExit();
        logger.addAppender(new FileAppender(file2.toString()));
        logger.setLevel(LogLevel.INFO);

        int threads = 20, perThread = 500;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CyclicBarrier gate = new CyclicBarrier(threads);   // everyone starts logging at the same moment
        CountDownLatch done = new CountDownLatch(threads);

        for (int t = 0; t < threads; t++) {
            final int id = t;
            pool.submit(() -> {
                try {
                    gate.await();
                    for (int m = 0; m < perThread; m++) {
                        logger.info("T" + id + "-M" + m);
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }
        done.await();
        pool.shutdown();

        List<String> lines2 = Files.readAllLines(file2);
        Pattern wellFormed = Pattern.compile(
                "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3} INFO  \\[[^\\]]+\\] T\\d+-M\\d+$");

        int malformed = 0;
        Set<String> seen = new HashSet<>();
        for (String line : lines2) {
            if (wellFormed.matcher(line).matches()) {
                seen.add(line.substring(line.lastIndexOf(' ') + 1));   // the "T3-M42" token
            } else {
                malformed++;
            }
        }

        int expected = threads * perThread;
        check("file has exactly " + expected + " lines", lines2.size() == expected, "got " + lines2.size());
        check("every line is well-formed (no interleaving)", malformed == 0, malformed + " malformed lines");
        check("every message appears exactly once (none lost or duplicated)",
                seen.size() == expected, "distinct messages: " + seen.size());

        System.out.println();
        System.out.println(failures == 0 ? "ALL CHECKS PASSED" : failures + " CHECK(S) FAILED");
    }

    private static void check(String name, boolean ok, String failureDetail) {
        if (ok) System.out.println("PASS: " + name);
        else { failures++; System.out.println("FAIL: " + name + " (" + failureDetail + ")"); }
    }
}
