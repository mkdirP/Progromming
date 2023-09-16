package mypokemons;

import mymoves.nosepass.*;
import ru.ifmo.se.pokemon.*;

public class Nosepass extends Pokemon{

    public Nosepass(String name, int level) {
        super(name,level);
        
        super.setType(Type.ROCK);
        super.setStats(30,45,135,45,90,30);

        Rest rest = new  Rest(0,0);
        Confide confide = new Confide(0,0);
        Facade facade = new Facade(70,100);

        super.setMove(rest,confide,facade);
    } 
    
}
