import java.util.*;

public class UserManager {
    private static UserManager instance = null;
    private List<String> onlineUsers;

    private UserManager() {
        onlineUsers = new ArrayList<>();
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public synchronized void addUser(String username) {
        if (!onlineUsers.contains(username)) {
            onlineUsers.add(username);
        }
    }

    public synchronized void removeUser(String username) {
        onlineUsers.remove(username);
    }

    public synchronized List<String> getOnlineUsers() {
        return new ArrayList<>(onlineUsers);
    }

    public synchronized boolean isUserOnline(String username) {
        return onlineUsers.contains(username);
    }
}
