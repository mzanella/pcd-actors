package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.impl.ActorSystemImpl;
import it.unipd.math.pcd.actors.impl.LocalActorRef;
import it.unipd.math.pcd.actors.utils.ActorSystemFactory;
import it.unipd.math.pcd.actors.utils.actors.StoreActor;
import it.unipd.math.pcd.actors.utils.actors.ping.pong.PingPongActor;
import it.unipd.math.pcd.actors.utils.messages.StoreMessage;
import it.unipd.math.pcd.actors.utils.messages.ping.pong.PingMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Marco Zanella
 * @date 04/01/16
 */
public class Prova {
    private ActorSystem system;
    private ActorRef<Message> ar1;
    private ActorRef<Message> ar0;

    /**
     * Initializes the {@code system} with a concrete implementation before each test.
     */
    @Before
    public void init() {
        this.system = new ActorSystemImplP();
        this.ar0 = (ActorRef<Message>)system.actorOf(ActorImp.class);
        this.ar1 = (ActorRef<Message>)system.actorOf(ActorImp.class);
    }

    @Test
    public void shouldBeAbleToSendAMessageToSelf() throws InterruptedException {
        ar0.send(new Name(0),ar0);
        Thread.sleep(1000);
        Assert.assertEquals("The message has to be received by the same actor that send the message",
                ((ActorImp)(((TestRef)ar0).getActor())).getName(), ((ActorImp)(((TestRef)ar0).getActor())).getLastS());
        /*int randomNum = (int)(Math.random()*100);
        for(int i=0; i<50; i++) {
            ar1.send(new N(), ar1);
            ar0.send(new N(), ar1);
            if (i == randomNum) ;
            //    s.stop(ar1);
        }
        Thread.sleep(1000);
        // Verify that the message is been processed
        Assert.assertEquals("The message has to be received by the actor", "Hello World", actor.getData());*/
    }


}


class ActorSystemImplP extends AbsActorSystem {
    private ExecutorService e;

    public ActorSystemImplP() { e = Executors.newCachedThreadPool(); }

    @Override
    protected ActorRef createActorReference(ActorMode mode)
    {
        if(mode == ActorMode.LOCAL) return new TestRef(this);
        else return null;
    }

    public ThreadFactory getSystemThreadFactory() { return Executors.defaultThreadFactory(); }
}

class TestRef extends LocalActorRef{
    @Override
    public int compareTo(ActorRef o) { return this.equals(o)?0:-1; }

    public TestRef(ActorSystem system) {
        super(system);
    }

    public Actor getActor(){return system.match(this);}
}

class Name implements Message{
    public int name;

    public Name(int n){
        name = n;
    }
}
class M implements Message{}
class N implements Message{}

class ActorImp extends AbsActor<Message> {
    private static int n = 0;
    private int name;
    private int lastS;
    private String message;

    public ActorImp() {
        name = n;
        n++;
        System.out.println(name);
    }

    public int getName() {
        return name;
    }

    public void setLastS(int s){
        lastS = s;
    }

    public int getLastS(){
        return lastS;
    }

    @Override
    public void receive(Message m) {
        if (m instanceof M)
            message = "si";
        else if (m instanceof N) {
            self.send(new M(), sender);
            message = "no";
        }
        else if (m instanceof Name){
            lastS = ((Name) m).name;
        }
    }
}

