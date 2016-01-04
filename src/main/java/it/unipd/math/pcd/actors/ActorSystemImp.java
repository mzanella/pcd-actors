package it.unipd.math.pcd.actors;

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

public class ActorSystemImp extends AbsActorSystem {
    private ExecutorService e;
    private Thread killer;
    private ThreadFactory tf;

    public ActorSystemImp() {
        tf = Executors.defaultThreadFactory();
        killer = tf.newThread(new Runnable{
            public void run(){
                while(true){

                }
            }
        });
        e = Executors.newCachedThreadPool();

    }

    @Override
    protected ActorRef createActorReference(ActorMode mode)
    {
        if(mode == ActorMode.LOCAL) {
            LocalActorRef lar = new LocalActorRef(this);
            e.execute(lar);
            return lar;
        } else {
            return null;
        }
    }
}