package l3s;

import java.util.Objects;

public class Animal extends Creatrue implements Behavior,Thingking{
    
    public Animal(String name){
        super(name);
    }
    
    public void callOut(Animal ChristopherRobin){
        System.out.println(name +" call out to " + ChristopherRobin);  
    }
    public void yellAgain(String content){
        System.out.println(name +  " yelled again " + content);
    }
    public void steppBack(){
        System.out.println(name + " stepp back");
    }
    public void shadeEyes(String content){
        System.out.println(name + " shading eyes " + content);
    }
    public void shout(String content){
        System.out.println(name + " shout " + content);
    }
    public void lookAtTheTop(){
        System.out.println(name + " look at the top");
    }
    public void cameAround(){
        System.out.println(name + " came aroud");
    }
    public void listen(){
        System.out.println(name + " listened and " + Places.forest + " was silent and quiet");
    }
    public void sing(){
        System.out.println(Places.somewhere+" "+name + " was singing");
    }
    
    public String toString(){
		return name;
	}
    public boolean equals(Object otherObject){
		if(this==otherObject){
			return true;
        }
        if(otherObject==null){
			return false;
		}
		if (getClass()!=otherObject.getClass()){
			return false;
		}
        Animal animal = (Animal) otherObject;  
	    return Objects.equals(name, animal.name); 
    }	
	public int hashCode(){
		return name != null ? name.hashCode() : 0;
	}
}
