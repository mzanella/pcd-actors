package it.unipd.math.pcd.actors;

public class LocalActorRef<T extends Message> extends AbsActorRef<T> implements Runnable {
    private AbsActorSystem actors;

    LocalActorRef(ActorSystem actors){
        this.actors = (AbsActorSystem)actors;
    }

    @Override
    public int compareTo(ActorRef o) {
        return (actors.match(this).equals(actors.match(o)))? 0 : -1;
    }

    @Override
    public void run() {
        while (!isTerminated()){
            Mail<T> mail = pop();
            AbsActor a = (AbsActor)actors.match(this);
            synchronized (a) {
                a.setSender(mail.getSender());
                a.receive(mail.getMessage());
            }
        }
    }
}
