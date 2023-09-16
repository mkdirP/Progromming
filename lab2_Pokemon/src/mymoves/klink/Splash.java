package mymoves.klink;

import ru.ifmo.se.pokemon.*;

public class Splash extends StatusMove {
    public Splash(double pow,double acc) {

        super(Type.NORMAL,pow,acc);
    }

    protected String describe() {
        String[] des = this . getClass().toString().split("\\.");
        return "uses " + des[des.length-1];
    }
}
