package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.impl.ActorSystemImp;
import it.unipd.math.pcd.actors.utils.actors.StoreActor;
import it.unipd.math.pcd.actors.utils.messages.StoreMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marco Zanella
 * @date 19/01/16
 */
public class MailboxTest {
    private ActorSystem system;

    /**
     * Initializes the {@code system} with a concrete implementation before each test.
     */
    @Before
    public void init() {
        this.system = new ActorSystemImp();
    }

    @Test
    public void shouldEmptyTheMailboxAndPutNewMessages() throws InterruptedException {
        TestActorRef ref = new TestActorRef(system.actorOf(StoreActor.class));
        StoreActor actor = (StoreActor) ref.getUnderlyingActor(system);
        //for(int i = 0; i < 100; i++)
            ref.send(new StoreMessage("Hello World"), ref);
        Thread.sleep(2000);
        //Assert.assertEquals("The mailbox must not be empty 1", false, actor.isEmptyMailbox());
        actor.emptyTheMailbox();
        Thread.sleep(2000);
        Assert.assertEquals("The mailbox must be empty", true, actor.isEmptyMailbox());
        //for(int i = 0; i < 100; i++)
            ref.send(new StoreMessage("Hello World"), ref);
        Assert.assertEquals("The mailbox must not be empty", false, actor.isEmptyMailbox());
    }
}