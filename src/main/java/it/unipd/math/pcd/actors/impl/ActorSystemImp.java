package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.AbsActorSystem;
import it.unipd.math.pcd.actors.Actor;
import it.unipd.math.pcd.actors.ActorRef;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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
        else throw new IllegalArgumentException();
    }

    /**
     * execute the Runnable passed on a thread on the cached Thread Pool
     * @param r type Runnable
     */
    public void systemExecute(Runnable r){e.execute(r);}

    /**
     * search the actor inside the actor system map, then it create a future task in order to empty the
     * mailbox of the actor that must be stopped. Finally it remove the actor from the map.
     * @param actor The actor to be stopped
     */
    @Override
    public void stop(ActorRef<?> actor) {
        final AbsActor<?> tostop = ((AbsActor<?>)match(actor));
        FutureTask<Boolean> f = new FutureTask<>(new Stopper(tostop));
        e.execute(f);
        try {
            f.get();
        } catch (InterruptedException|ExecutionException e1) {
            e1.printStackTrace();
        }
        actors.remove(actor);
    }

    /**
     * for each actor in the actor system map create a future task in order to create a
     * Stopper(class derived from Callable) that have to empty the mailbox.
     * Finally it clear the ActorSystem actor map
     */
    @Override
    public void stop() {
        List<FutureTask<Boolean>> futureStop = new LinkedList<>();
        FutureTask<Boolean> f;
        for (Map.Entry<ActorRef<?>, Actor<?>> entry : actors.entrySet()) {
            f = new FutureTask<>(new Stopper((AbsActor<?>) entry.getValue()));
            futureStop.add(f);
            e.execute(f);
        }
        for (FutureTask<Boolean> e : futureStop) {
            try {
                e.get();
            } catch (InterruptedException | ExecutionException e1) {
                e1.printStackTrace();
            }
        }
        actors.clear();
    }

    /**
     * class that implements callable in order to stop actors. This must avoid errors derived
     * by some actors that are working but they are erased from the map
     */
    private static class Stopper implements Callable<Boolean>{
        private AbsActor<?> actor;

        Stopper(AbsActor<?> a){
            actor = a;
        }

        @Override
        public Boolean call() throws Exception {
            return actor.stop();
        }
    }
}
