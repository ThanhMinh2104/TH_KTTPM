package com.example.demo;

public interface Observer<T> {
    void update(T subject, Object data);
}

