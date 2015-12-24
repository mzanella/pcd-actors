package it.unipd.math.pcd.actors;

/**
 * interface for mailbox
 *
 * @author Marco Zanella
 * @date 21/12/15
 */

public interface Mail<T extends Message> {
    T getMessage();
    ActorRef<T> getSender();
}
