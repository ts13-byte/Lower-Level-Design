package PracticeProblems.LoggingFramework;

public enum LogLevel {
    /**
     * In the increasing level of the severity.
     */
    DEBUG , INFO , WARN , ERROR;

    /**
     * this method checks if the log level of the message is above the min level of the logger set by the user.
     * @return true/faslse
     */
    public boolean isAtLeast(LogLevel min) {
        return this.ordinal() >= min.ordinal();
    }
}
