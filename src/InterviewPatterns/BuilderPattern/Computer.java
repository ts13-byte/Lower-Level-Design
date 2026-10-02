package InterviewPatterns.BuilderPattern;

public class Computer {
    private final String brand;
    private final String processor;

    private final String ram;
    private final String storage;
    private final String gpu;
    private final String os;
    private final String bluetooth;
    private final String wifi;

    private Computer(Builder builder) {
        this.brand = builder.brand;
        this.processor = builder.processor;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.os = builder.os;
        this.bluetooth = builder.bluetooth;
        this.wifi = builder.wifi;
    }

    public static class Builder {
        private final String brand;
        private final String processor;

        private String ram;
        private String storage;
        private String gpu;
        private String os;
        private String bluetooth;
        private String wifi;

        public Builder(String brand , String processor) {
            this.brand = brand;
            this.processor = processor;
        }

        public Builder ram(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder os(String os) {
            this.os = os;
            return this;
        }

        public Builder bluetooth(String bluetooth) {
            this.bluetooth = bluetooth;
            return this;
        }

        public Builder wifi(String wifi) {
            this.wifi = wifi;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

    @Override
    public String toString() {
        return "Computer{" +
                "brand='" + brand + '\'' +
                ", processor='" + processor + '\'' +
                ", ram='" + ram + '\'' +
                ", storage='" + storage + '\'' +
                ", gpu='" + gpu + '\'' +
                ", os='" + os + '\'' +
                ", bluetooth='" + bluetooth + '\'' +
                ", wifi='" + wifi + '\'' +
                '}';
    }
}
