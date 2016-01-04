package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.impl.ActorSystemImpl;
import it.unipd.math.pcd.actors.impl.IMail;
import it.unipd.math.pcd.actors.impl.Mail;

import java.util.concurrent.ThreadFactory;

/**
 * @author Marco Zanella
 * @date 04/01/16
 */
public abstract class AbsActorRef<T extends Message> implements ActorRef<T>{
    protected AbsActorSystem system;

    public AbsActorRef(ActorSystem system){ this.system = (AbsActorSystem)system; }

    @Override
    public void send(T message, ActorRef to) {
        ((AbsActorRef)to).putInTheMailbox(new IMail<T>(message, this));
    }

    private void putInTheMailbox(Mail<T> m){
        ((AbsActor<T>)system.match(this)).addInTheMailbox(m);
    }

    public ThreadFactory getSystemThreadFactory() { return ((ActorSystemImpl)system).getSystemThreadFactory(); }
}
