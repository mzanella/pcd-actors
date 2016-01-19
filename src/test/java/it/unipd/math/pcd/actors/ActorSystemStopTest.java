package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;
import it.unipd.math.pcd.actors.utils.ActorSystemFactory;
import it.unipd.math.pcd.actors.utils.actors.TrivialActor;
import it.unipd.math.pcd.actors.utils.messages.TrivialMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marco Zanella
 * @date 19/01/16
 */
public class ActorSystemStopTest {
    private ActorSystem system;

    /**
     * Initializes the {@code system} with a concrete implementation before each test.
     */
    @Before
    public void init() {
        system = ActorSystemFactory.buildActorSystem();
    }

    @Test(expected = NoSuchActorException.class)
    public void shouldStopAllActorsAndTheseCouldNotBeAbleToReceiveNewMessages() {
        ActorRef ref1 = system.actorOf(TrivialActor.class);
        ActorRef ref2 = system.actorOf(TrivialActor.class);
        for(int i = 0; i < 20; i++){
            ref1.send(new TrivialMessage(), ref2);
            ref1.send(new TrivialMessage(), ref2);
        }
        system.stop();
        ref1.send(new TrivialMessage(), ref1);
    }

    @Test(expected = NoSuchActorException.class)
    public void shouldStopAllActorsAndTheseCouldNotStoppedASecondTime() {
        ActorRef ref1 = system.actorOf(TrivialActor.class);
        ActorRef ref2 = system.actorOf(TrivialActor.class);
        for(int i = 0; i < 20; i++){
            ref1.send(new TrivialMessage(), ref2);
            ref1.send(new TrivialMessage(), ref2);
        }
        system.stop();
        system.stop(ref1);
    }
}
