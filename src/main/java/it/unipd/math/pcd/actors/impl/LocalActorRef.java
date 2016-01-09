package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;

import java.util.concurrent.ThreadFactory;

public class LocalActorRef<T extends Message> implements ActorRef<T> {

    protected AbsActorSystem system;

    public LocalActorRef(ActorSystem system){ this.system = (AbsActorSystem)system; }

    @Override
    public void send(T message, ActorRef to) {((AbsActor<T>)system.match(to)).addInTheMailbox(new IMail<T>(message, this));}

    @Override
    public int compareTo(ActorRef o) { return this.equals(o)?0:-1; }

    public ThreadFactory getSystemThreadFactory() { return ((ActorSystemImp)system).getSystemThreadFactory(); }
}
