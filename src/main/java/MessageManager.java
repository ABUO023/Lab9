import java.util.*;

public class MessageManager {
    private static MessageManager instance = null;
    private List<Message> messages;

    public static class Message {
        public String sender;
        public String text;
        public long timestamp;

        public Message(String sender, String text) {
            this.sender = sender;
            this.text = text;
            this.timestamp = System.currentTimeMillis();
        }
    }

    private MessageManager() {
        messages = new ArrayList<>();
    }

    public static synchronized MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public synchronized void addMessage(String sender, String text) {
        messages.add(new Message(sender, text));
    }

    public synchronized List<Message> getMessages(int startIndex) {
        List<Message> result = new ArrayList<>();
        for (int i = startIndex; i < messages.size(); i++) {
            result.add(messages.get(i));
        }
        return result;
    }

    public synchronized int getMessageCount() {
        return messages.size();
    }
}
