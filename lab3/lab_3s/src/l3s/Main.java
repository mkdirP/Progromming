package l3s;

public class Main extends Animal{
    public Main(String name) {
        super(name);
    }
    public static void main(String args[]){
        Animal Bunny = new Animal("Bunny");   
        Animal ChristopherRobin = new Animal("Christopher Robin");     
        Animal жаворонок = new Animal("жаронок");
        
	    Bunny.callOut(ChristopherRobin);
        Bunny.yellAgain("\"Listen! It's Bunny!\"");  
        Bunny.steppBack();  
        Bunny.shadeEyes("with his paw");
        Bunny.shout("some more");  
        Bunny.lookAtTheTop();  
        Bunny.cameAround();  
        Bunny.listen();  
        жаворонок.sing();	  
    }
       
}
