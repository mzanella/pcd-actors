package it.unipd.math.pcd.actors;

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
            if (i == randomNum)
                s.stop(ar1);
        }
    }
}

class M implements Message{}
class N implements Message{}

class ActorImp extends AbsActor<Message> {

    @Override
    public void receive(Message m) {
        if (m instanceof M)
            System.out.println(this + " M:risposto");
        else if (m instanceof N) {
            self.send(new M(), sender);
            System.out.println(this + " N:inviato");
        }
    }
}