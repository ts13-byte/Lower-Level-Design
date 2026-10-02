package InterviewPatterns.StrategyPattern;

public class NetBankingPayment implements PaymentStrategy{

    private String bankName;
    private String accountNumber;
    private String ifscCode;

    public NetBankingPayment(String bankName, String accountNumber, String ifscCode) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
    }

    @Override
    public void pay(double amount) {
        System.out.println("paying " + amount + " via " + accountNumber);
    }
}
