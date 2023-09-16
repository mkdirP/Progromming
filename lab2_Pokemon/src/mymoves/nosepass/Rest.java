package mymoves.nosepass;

import ru.ifmo.se.pokemon.*;

import static java.lang.Math.round;
import static ru.ifmo.se.pokemon.Effect.sleep;

public class Rest extends StatusMove {
    public Rest (double pow,double acc) {
        super(Type.PSYCHIC,pow,acc);
    }
    int a;
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        sleep(p);
        Effect g = new Effect();
        g.stat(Stat.HP,(int) round(p.getStat(Stat.HP)-p.getHP()));
        p.addEffect(g);
        Math.round(p.getStat(Stat.HP)-p.getHP());
    }
    
    protected String describe() {
        String[] des = this.getClass().toString().split("\\.");
        return "uses " + des[des.length - 1]+" and HP+ "+a;
    }
    
}

   
