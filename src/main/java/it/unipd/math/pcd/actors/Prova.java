package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.impl.ActorSystemImp;

public class Prova {
    public static void main(String[] args) {
        ActorSystem s = new ActorSystemImp();
        ActorRef<Message> ar1 = (ActorRef<Message>)s.actorOf(ActorImp.class);
        ActorRef<Message> ar2 = (ActorRef<Message>)s.actorOf(ActorImp.class);
        int randomNum = (int)(Math.random()*50);
        System.out.println(randomNum);
        for(int i=0; i<50; i++) {
            ar1.send(new N(), ar2);
            ar2.send(new N(), ar1);
            if (i == randomNum) ;
            //    s.stop(ar1);
        }
        //ar1.send(new M(), ar2);
    }
}

class M implements Message{}
class N implements Message{}

class ActorImp extends AbsActor<Message> {
    private static int n = 0;
    private int name;
    public ActorImp() {
        name = n;
        n++;
        System.out.println(name);
    }

    @Override
    public void receive(Message m) {
        if (m instanceof M)
            System.out.println(name + ":risposto");
        else if (m instanceof N) {
            self.send(new M(), sender);
            System.out.println(name + ":inviato");
        }
    }
}