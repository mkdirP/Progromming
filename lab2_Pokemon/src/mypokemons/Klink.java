package mypokemons;

import mymoves.klink.WaterGun;
import mymoves.tapufini.DoubleTeam;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Klink extends Pokemon{

    public Klink(String name, int level) {
        super(name,level);
        
        super.setType(Type.STEEL);
        super.setStats(40,55,70,45,60,30);

        WaterGun waterGun = new WaterGun(40,100);
        DoubleTeam doubleTeam = new DoubleTeam(0,0);

        super.setMove(waterGun,doubleTeam);
    } 
    
}
