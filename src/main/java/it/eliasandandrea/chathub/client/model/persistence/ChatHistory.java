package it.eliasandandrea.chathub.client.model.persistence;

import it.eliasandandrea.chathub.client.view.chatComponents.MessageEntry;

import java.util.HashMap;
import java.util.LinkedList;

public class ChatHistory {

    private static ChatHistory instance;

    public static ChatHistory getInstance() {
        if(instance == null) {
            instance = new ChatHistory();
        }
        return instance;
    }

    public HashMap<String, LinkedList<MessageEntry>> messagesRepository = new HashMap<>();

    public LinkedList<MessageEntry> getMessages(String chatUUID) {
        return messagesRepository.getOrDefault(chatUUID, new LinkedList<>());
    }

    public void addMessage(String chatUUID, MessageEntry messageEntry) {
        if(messagesRepository.containsKey(chatUUID)) {
            messagesRepository.get(chatUUID).add(messageEntry);
        } else {
            LinkedList<MessageEntry> messages = new LinkedList<>();
            messages.add(messageEntry);
            messagesRepository.put(chatUUID, messages);
        }
    }
}
