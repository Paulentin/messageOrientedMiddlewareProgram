package com.zabara.messaging.homework.rabbit;

import com.zabara.messaging.homework.rabbit.controller.Receipt;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;

import java.util.List;
@AllArgsConstructor
public class FailedMessageDataStore {
    private List<Message<Receipt>> failedMessages;

    public void putMessage(Message<Receipt> message) {
        failedMessages.add(message);
    }

    public List<Message<Receipt>> getMessages() {
        return failedMessages;
    }
}
