/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;
import it.unipd.math.pcd.actors.impl.AbsActorRef;
import it.unipd.math.pcd.actors.impl.LocalActorRef;
import it.unipd.math.pcd.actors.impl.Mail;

import java.util.concurrent.*;
/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActor<T extends Message> implements Actor<T> {

    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /**
     * List of the Mail received
     */
    private BlockingQueue<Mail<T>> mailbox;

    /**
     * true if terminated by the actor system
     */
    private volatile boolean terminated;

    /**
     * true if the MailManager is created
     */
    private volatile boolean createManager;

    /**
     * create AbsActor, in particular create the mailbox and set to false the attributes terminated and createManager
     */
    AbsActor(){
        mailbox = new LinkedBlockingQueue<>();
        self = null;
        sender = null;
        terminated = false;
        createManager = false;
    }

    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }

    /**
     * @param sender
     * set sender to the last actoref that send message to this actor
     */
    protected final void setSender(ActorRef<T> sender) {
        this.sender = sender;
    }

    /**
     * @param mail
     * @throws NoSuchActorException if try to add in the mailbox a mail and the actor is terminated
     */
    public void addInTheMailbox(Mail<T> mail){
        try {
            if (terminated)
                throw new NoSuchActorException();
            mailbox.put(mail);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!createManager) {
            createTheMailboxManager();
        }
    }


    /**
     * create the mailboxManager
     */
    private synchronized void createTheMailboxManager() {
            ((AbsActorRef<T>)self).execute(new MailboxManager());
            createManager = true;
    }

    public void stop() { terminated = true; }


    /**
     * class that implements runnable in order to manage the message in the mailbox
     */
    private class MailboxManager implements Runnable {

        /**
         * if the actor isn't terminated then the mailbox manager work.
         * if the actor is terminated the mailbox manager must empty the mailbox
         */
        @Override
        public void run() {
            while(!terminated)
               mailManagement();
            if (terminated)
                while(!(mailbox.isEmpty()))
                    mailManagement();
        }

        /**
         * this method take a mail from the mailbox, set sender in the actor and invoke on
         * the actor receive of the message correlate with the sender set
         */
        private void mailManagement(){
            try {
                Mail m = mailbox.take();
                setSender(m.getSender());
                receive((T) m.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
