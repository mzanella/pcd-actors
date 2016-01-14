package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

/**
 * interface for mailbox
 * it is used to pair the message sent by an Actor, through his ActorRef, and the ActorRef
 *
 * @author Marco Zanella
 * @date 21/12/15
 */

public interface Mail<T extends Message> {
    T getMessage();
    ActorRef<T> getSender();
}
