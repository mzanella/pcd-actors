package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsActorSystem;
import it.unipd.math.pcd.actors.ActorRef;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * concrete class of ActorSystem
 * this class use Executors for execute runnable. This is used to execute the MailboxManager of all Actor
 *
 * @author Marco Zanella
 * @date 24/12/15
 */

public class ActorSystemImp extends AbsActorSystem {
    private ExecutorService e;

    /**
     * the constructor creates inizialize e to a new cached thread pool in order to execute all MailboxManager
     */
    public ActorSystemImp() { e = Executors.newCachedThreadPool(); }

    /**
     * method to create new ActorRef
     * @param mode
     * @return ActorRef
     */
    @Override
    protected ActorRef createActorReference(ActorMode mode)
    {
        if(mode == ActorMode.LOCAL) return new LocalActorRef(this);
        else return null;
    }

    /**
     * execute the Runnable passed on a thread on the cached Thread Pool
     * @param r type Runnable
     */
    public void systemExecute(Runnable r){e.execute(r);}
}
