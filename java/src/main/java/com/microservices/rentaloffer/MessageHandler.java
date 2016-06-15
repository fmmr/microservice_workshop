package com.microservices.rentaloffer;

public interface MessageHandler extends Client{
    void handle(String message);
}
