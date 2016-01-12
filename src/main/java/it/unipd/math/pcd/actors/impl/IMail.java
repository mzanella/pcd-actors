package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

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

    /**
     * Return the message in the mail
     * @return T subtype of Message
     */
    @Override
    public T getMessage() {
        return message;
    }

    /**
     * Return the sender of the Message message
     * @return ActorRef
     */
    @Override
    public ActorRef<T> getSender() {
        return sender;
    }
}
