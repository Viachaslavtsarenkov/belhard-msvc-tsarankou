package by.tsarankou.serviceresource.client;

public interface MessagePublisher {
    void postMessage(Object message);
    void deleteMessage(Object message);

}
