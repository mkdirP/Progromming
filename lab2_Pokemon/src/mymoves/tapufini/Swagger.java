package mymoves.tapufini;

import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {
    public Swagger (double pow,double acc) {
        super(Type.NORMAL,pow,acc);
    }

    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);
        Effect.confuse(p);
    }

    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        Effect effect = new Effect().stat(Stat.ATTACK, 2);

        p.addEffect(effect);
    }
    
    protected String describe() {
        String[] des = this . getClass().toString().split("\\.");
        return "uses " + des[des.length-1];
    }  
}
