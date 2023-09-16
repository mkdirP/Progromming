package mymoves.nosepass;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade (double pow, double acc) {
        super(Type.NORMAL, pow, acc);
    }

    protected void applyOppEffects(Pokemon p){  
        if(p.getCondition()== Status.BURN||p.getCondition()==Status.PARALYZE||p.getCondition()==Status.POISON){  
        	new Facade(140,100);  
        }
        else{  
            new Facade(70,100);  
        }  
    }  
    protected String describe () {  
    	String[] des = this.getClass().toString().split("\\.");  
        return "uses " + des[des.length - 1];  
    }  
}
