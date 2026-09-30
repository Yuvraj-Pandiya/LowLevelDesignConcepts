import java.util.ArrayList;
import java.util.List;

// --- OBSERVER INTERFACE ---
interface ISubscriber {
    void update(String videoTitle);
    String getName();
}

// --- SUBJECT / OBSERVABLE INTERFACE ---
interface IChannel {
    void registerSubscriber(ISubscriber subscriber);
    void removeSubscriber(ISubscriber subscriber);
    void notifySubscribers();
}

// --- CONCRETE SUBJECT ---
class YouTubeChannel implements IChannel {
    private List<ISubscriber> subscribers = new ArrayList<>();
    private String channelName;
    private String latestVideoTitle;

    public YouTubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void registerSubscriber(ISubscriber subscriber) {
        subscribers.add(subscriber);
        System.out.println(subscriber.getName() + " subscribed to " + channelName);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        subscribers.remove(subscriber);
        System.out.println(subscriber.getName() + " unsubscribed from " + channelName);
    }

    // PUSH MECHANISM: The subject initiates the broadcast, eliminating wasteful polling
    @Override
    public void notifySubscribers() {
        for (ISubscriber subscriber : subscribers) {
            subscriber.update(latestVideoTitle);
        }
    }

    public void uploadVideo(String videoTitle) {
        this.latestVideoTitle = videoTitle;
        System.out.println("\n[Notification] " + channelName + " uploaded a new video: " + videoTitle);
        notifySubscribers(); 
    }
}

// --- CONCRETE OBSERVER ---
class UserAccount implements ISubscriber {
    private String userName;

    public UserAccount(String userName) {
        this.userName = userName;
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("   Hey " + userName + ", a new video is out: \"" + videoTitle + "\"");
    }
}

// --- DEMO RUNNER ---
public class ObserverDemo {
    public static void main(String[] args) {
        // Create the Observable (Subject)
        YouTubeChannel techChannel = new YouTubeChannel("TechExplained");

        // Create Observers
        ISubscriber user1 = new UserAccount("Ram");
        ISubscriber user2 = new UserAccount("Shyam");
        ISubscriber user3 = new UserAccount("kapil");

        // Establish One-to-Many Relationships
        techChannel.registerSubscriber(user1);
        techChannel.registerSubscriber(user2);
        techChannel.registerSubscriber(user3);

        // Subject state changes -> Pushes automated updates to dependents
        techChannel.uploadVideo("Design Patterns: Intro to Observer");

        // One observer unsubscribes
        System.out.println();
        techChannel.removeSubscriber(user2);

        // Next state change notifies remaining active dependents automatically
        techChannel.uploadVideo("Why Polling Architecture is Inefficient");
    }
}
