package mymoves.klink;

import ru.ifmo.se.pokemon.*;

public class WaterGun extends SpecialMove {
    public WaterGun(double pow,double acc) {
        
        super(Type.WATER, pow, acc);
    }

    protected String describe() {
        String[] des = this . getClass().toString().split("\\.");
        return "uses " + des[des.length-1];
    }  
}
