package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;

import java.util.concurrent.ThreadFactory;

public class LocalActorRef<T extends Message> extends AbsActorRef<T> {



    public LocalActorRef(ActorSystem system){ super((AbsActorSystem)system); }

    @Override
    public void send(T message, ActorRef to) {((AbsActor<T>)system.match(to)).addInTheMailbox(new IMail<T>(message, this));}


}
