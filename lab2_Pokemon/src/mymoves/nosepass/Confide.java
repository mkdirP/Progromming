package mymoves.nosepass;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {
    public Confide (double pow,double acc) {
        
        super(Type.NORMAL,pow,acc);
    }

    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);
        Effect effect = new Effect().stat(Stat.ATTACK, -1);

        p.addEffect(effect);
    }
    
    protected String describe() {
        String[] des = this . getClass().toString().split("\\.");
        return "uses " + des[des.length-1];
    } 
    
}
