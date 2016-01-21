package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;

import java.util.concurrent.ThreadFactory;

public class LocalActorRef<T extends Message> extends AbsActorRef<T> {

    public LocalActorRef(ActorSystem system){ super(system); }

    /**
     * the message that must be send are added int the mailbox of the receiver. to keep a reference of
     * the sender it crete a IMail. this class is only used to be a wrapper for an actor reference and a message
     * @param message The message to send
     * @param to The actor to which sending the message
     */
    @Override
    public void send(T message, ActorRef to) {((AbsActor<T>)system.match(to)).addInTheMailbox(new IMail<T>(message, this));}

}
