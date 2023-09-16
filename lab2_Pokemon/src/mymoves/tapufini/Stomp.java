package mymoves.tapufini;

import lab2.program;
import ru.ifmo.se.pokemon.*;

public class Stomp extends PhysicalMove {
    public Stomp (double pow,double acc) {
        super(Type.NORMAL,pow,acc);
    }

    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);
        
        if(program.chance(0.3)) {
            Effect.paralyze(p);
        }     
    }

    protected String describe() {
        String[] des = this . getClass().toString().split("\\.");
        return "uses " + des[des.length-1];
    }
    
}
