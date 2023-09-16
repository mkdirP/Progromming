package mymoves.klink;

import lab2.program;
import ru.ifmo.se.pokemon.*;

public class WaterFall extends PhysicalMove{
    public WaterFall (double pow,double acc) {

        super(Type.WATER,pow,acc);
    }

    protected void applyOppEffects (Pokemon p){
        super.applyOppEffects(p);
        if (program.chance(0.2)){
            Effect.flinch(p);
        }
    }

    protected String describe() {
        String[] des = this . getClass().toString().split("\\.");
        return "uses " + des[des.length-1];
    }
}

   