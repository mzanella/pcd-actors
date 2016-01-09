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
package it.unipd.math.pcd.actors.utils.actors.ping.pong;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.utils.messages.ping.pong.PingMessage;
import it.unipd.math.pcd.actors.utils.messages.ping.pong.PingPongMessage;
import it.unipd.math.pcd.actors.utils.messages.ping.pong.PongMessage;

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public class PingPongActor extends AbsActor<PingPongMessage> {

    private PingPongMessage lastMessage;

    public PingPongMessage getLastMessage() {
        if (lastMessage == null)
            throw new RuntimeException("message not found");
        return lastMessage;
    }

    /**
     * Responds to a {@link PingMessage} with a {@link PongMessage}.
     *
     * @param message The type of messages the actor can receive
     */
    @Override
    public void receive(PingPongMessage message) {
        this.lastMessage = message;
        if (message instanceof PingMessage){System.out.println("received \n");
            self.send(new PongMessage(), sender);}
    }
}
