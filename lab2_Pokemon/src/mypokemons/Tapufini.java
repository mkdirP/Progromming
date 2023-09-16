package mypokemons;

import mymoves.tapufini.*;
import ru.ifmo.se.pokemon.*;

public class Tapufini extends Pokemon {
   
    public Tapufini(String name, int level) {
        super(name,level);
        
        super.setType(Type.WATER,Type.FAIRY);
        super.setStats(70,75,115,95,130,85);

        DoubleTeam doubleTeam = new  DoubleTeam(0,0);
        BodySlam bodySlam = new BodySlam(85,100);
        Stomp stomp = new Stomp(65,100);
        Swagger swagger = new Swagger(0,85);

        super.setMove(doubleTeam,bodySlam,stomp,swagger);
    } 
}
