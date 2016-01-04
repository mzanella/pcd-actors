package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;

public class LocalActorRef<T extends Message> extends AbsActorRef<T> {

    public LocalActorRef(ActorSystem system){ super(system); }

    @Override
    public int compareTo(ActorRef o) { return this.equals(o)?0:-1; }
}
