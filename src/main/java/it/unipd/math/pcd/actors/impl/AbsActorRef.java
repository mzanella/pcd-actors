package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;

public abstract class AbsActorRef<T extends Message> implements ActorRef<T> {
    protected final AbsActorSystem system;

    public AbsActorRef(ActorSystem system){ this.system = (AbsActorSystem)system; }

    @Override
    public int compareTo(ActorRef o) { return (this == o)?0:-1; }

    protected abstract void execute(Runnable r);
}
