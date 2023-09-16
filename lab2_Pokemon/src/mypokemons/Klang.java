package mypokemons;

import mymoves.klink.*;
import mymoves.tapufini.DoubleTeam;
import ru.ifmo.se.pokemon.*;

public class Klang extends Pokemon{

    public Klang(String name, int level) {
        super(name,level);
        
        super.setType(Type.STEEL);
        super.setStats(40,55,70,45,60,30);

        WaterGun waterGun = new WaterGun(40,100);
        DoubleTeam doubleTeam = new DoubleTeam(0,0);
        Splash splash = new Splash(0,0);

        super.setMove(waterGun,doubleTeam,splash);
    } 
    
}
