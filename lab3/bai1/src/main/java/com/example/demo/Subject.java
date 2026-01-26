package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject<T> {

    protected List<Observer<T>> observers = new ArrayList<>();

    public void attach(Observer<T> observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Đã đăng ký observer: " + observer);
        }
    }

    public void detach(Observer<T> observer) {
        observers.remove(observer);
        System.out.println("Đã hủy đăng ký observer: " + observer);
    }

    protected void notifyObservers(Object data) {
        System.out.println("\nGửi thông báo đến " + observers.size() + " observer(s)...");
        for (Observer<T> observer : observers) {
            observer.update((T) this, data);
        }
    }
}

