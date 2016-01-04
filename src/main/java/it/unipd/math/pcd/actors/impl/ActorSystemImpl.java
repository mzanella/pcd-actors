package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsActorSystem;
import it.unipd.math.pcd.actors.ActorRef;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * concrete class of ActorSystem
 * this class use Executors for execute ActorRef
 *
 * @author Marco Zanella
 * @date 24/12/15
 */

public class ActorSystemImpl extends AbsActorSystem {
    private ExecutorService e;

    public ActorSystemImpl() { e = Executors.newCachedThreadPool(); }

    @Override
    protected ActorRef createActorReference(ActorMode mode)
    {
        if(mode == ActorMode.LOCAL) return new LocalActorRef(this);
        else return null;
    }

    public ThreadFactory getSystemThreadFactory() { return Executors.defaultThreadFactory(); }
}
