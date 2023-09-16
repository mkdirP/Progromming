package mypokemons;

import mymoves.nosepass.*;
import ru.ifmo.se.pokemon.*;

public class Probopass extends Pokemon{

    public Probopass(String name, int level) {
        super(name,level);
        
        super.setType(Type.ROCK,Type.STEEL);
        super.setStats(60,55,145,75,150,40);

        Rest rest = new  Rest(0,0);
        Confide confide = new Confide(0,0);
        Facade facade = new Facade(70,100);
        LeechLife leechLife = new LeechLife(80, 100);

        super.setMove(rest,confide,facade,leechLife);
    }   
}
