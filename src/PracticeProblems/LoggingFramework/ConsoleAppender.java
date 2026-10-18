package PracticeProblems.LoggingFramework;

public class ConsoleAppender implements LogAppender{
    @Override
    public void appendLog(LogMessage logMessage) {
        System.out.println(logMessage.format());
    }
}
