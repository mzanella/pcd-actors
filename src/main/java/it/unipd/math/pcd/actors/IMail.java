package it.unipd.math.pcd.actors;

/**
 * implememntazione della classe Mail per la gestione della mailbox
 *
 * @author Marco Zanella
 * @date 22/12/15
 */
public final class IMail<T extends Message> implements Mail<T> {
    private final T message;
    private final ActorRef<T> sender;

    public IMail(T message, ActorRef<T> sender) {
        this.message = message;
        this.sender = sender;
    }

    @Override
    public T getMessage() {
        return message;
    }

    @Override
    public ActorRef<T> getSender() {
        return sender;
    }
}
