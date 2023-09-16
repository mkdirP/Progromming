package Classes;

public class Coordinates {
    private Float x; //Максимальное значение поля: 60, Поле не может быть null
    private float y; //Значение поля должно быть больше -646

    @Override
    public String toString() {
        return x + "," + y;
    }

    public boolean set(Float x, float y) throws Exception {
        if (x > 60 || x < 0 || y <= -646){
            throw new Exception("x: (0,60], y: y>-646");
        }
        this.x = x;
        this.y = y;
        return true;
    }

}
