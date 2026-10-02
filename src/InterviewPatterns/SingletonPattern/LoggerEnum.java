package InterviewPatterns.SingletonPattern;

public enum LoggerEnum {
    INFO("INFO"),
    DEBUG("DEBUG"),
    ERROR("ERROR");

    private final String loggingLevel;

    LoggerEnum(String logginglevel) {
        this.loggingLevel = logginglevel;
    }

    public String getLoggingLevel() {
        return loggingLevel;
    }
}
