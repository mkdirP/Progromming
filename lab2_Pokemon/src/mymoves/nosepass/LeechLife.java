package mymoves.nosepass;

import ru.ifmo.se.pokemon.*;

public class LeechLife extends PhysicalMove {
    public LeechLife(double pow,double acc) {

        super(Type.BUG,pow,acc);
    }
    
    int a;
    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);
        Effect effect = new Effect().stat(Stat.ATTACK,a);

        p.addEffect(effect);
    }
    
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        Effect e = new Effect().stat(Stat.HP,1/2);
    }
    
    protected String describe() {
        String[] des = this . getClass().toString().split("\\.");
        return "uses " + des[des.length-1];
    }
    
}
