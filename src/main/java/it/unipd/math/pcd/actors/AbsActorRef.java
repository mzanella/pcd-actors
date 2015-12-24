package it.unipd.math.pcd.actors;

import java.util.concurrent.*;

/**
 * abstract class that implements ActorRef
 * it should factorize behaviors of both LocalActorRef and RemoteActorRef
 *
 * @author Marco Zanella
 * @date 21/12/15
 */

public abstract class AbsActorRef<T extends Message> implements ActorRef<T> {
    private volatile boolean terminated;
    private BlockingQueue<Mail<T>> mailbox;

    public AbsActorRef() {
        terminated = false;
        mailbox = new LinkedBlockingDeque<>();
    }

    @Override
    public void send(T message, ActorRef to) {
        if (!((AbsActorRef)to).terminated)
            ((AbsActorRef)to).addInTheMailbox(new IMail<>(message, this));
    }

    private void addInTheMailbox(Mail<T> mail) {
        try {
            mailbox.put(mail);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }

    public final synchronized void stop() {
        terminated = true;
    }

    public final boolean noMessage() {
        return mailbox.isEmpty();
    }

    public final boolean isTerminated() {
        return terminated;
    }

    protected final Mail pop() {
        Mail m = null;
        try {
            m = mailbox.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return m;
    }
}
