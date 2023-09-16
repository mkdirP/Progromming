package l5s.Classes;

public class Label {
    private String name;
    private Long bands; //Поле не может быть null

    @Override
    public String toString() {
        if (name == null){
            return bands.toString();
        }
        return name + "," + bands ;
    }

    public boolean set(String name, Long bands){
        if (bands == null){
            return false;
        }
        this.name = name;
        this.bands = bands;
        return true;
    }


}
