package mymoves.tapufini;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public DoubleTeam(double pow,double acc) {

        super(Type.NORMAL,pow,acc);
    }
     
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        Effect e = new Effect().stat(Stat.EVASION, 1);

        p.addEffect(e);
    }

    protected String describe() {
        String[] des = this . getClass().toString().split("\\.");
        return "uses " + des[des.length-1];
    }
}
