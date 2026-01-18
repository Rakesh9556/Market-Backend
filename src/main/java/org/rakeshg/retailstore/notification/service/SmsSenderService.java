package org.rakeshg.retailstore.notification.service;

public interface SmsSenderService {
    void send(String destination, String message);
}
