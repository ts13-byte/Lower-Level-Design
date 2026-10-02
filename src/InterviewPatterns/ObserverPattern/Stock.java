package InterviewPatterns.ObserverPattern;

import java.util.ArrayList;
import java.util.List;

public class Stock implements Subject{
    private String name;
    private double price;
    private List<Observer> observerList;

    Stock(String name) {
        this.name = name;
        observerList = new ArrayList<>();
    }

    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    }

    @Override
    public void attach(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observerList) {
            observer.update(name , price);
        }
    }
}
