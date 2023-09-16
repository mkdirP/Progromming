package lab2;

import mypokemons.*;
import ru.ifmo.se.pokemon.*;

public class program {

    public static void main(String[] args) {

        Battle b = new Battle();
        Tapufini p1 = new Tapufini("Tapufini", 100);
        Nosepass p2 = new Nosepass("Nosepass", 100);
        Probopass p3 = new Probopass("Nosepass", 100);
        Klink p4 = new Klink("Nosepass", 100);
        Klang p5 = new Klang("Nosepass", 100);
        Klinklang p6 = new Klinklang("Nosepass", 100);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();  
    }

    public static boolean chance(double d) {
        return d > Math.random();
    }
}
