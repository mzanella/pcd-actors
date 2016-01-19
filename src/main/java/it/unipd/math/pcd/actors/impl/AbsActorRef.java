package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;

/**
 * this class was created to keep method equals from Local and Remote ActorRef
 * @param <T>
 */
public abstract class AbsActorRef<T extends Message> implements ActorRef<T> {
    protected final AbsActorSystem system;

    public AbsActorRef(ActorSystem system){ this.system = (AbsActorSystem)system; }

    @Override
    public int compareTo(ActorRef o) { return (this == o)?0:-1; }

    /**
     * this method is used to run task only on the cached thread pool of the actor system
     * @param r Runnable that the system have to start
     */
    public void execute(Runnable r){system.systemExecute(r);}
}
