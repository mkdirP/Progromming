package Service;

import java.io.Serializable;

public class SerializableComArg implements Serializable {
    //反序列化对象
    private static final long serialVersionUID = 14L;
    private String comArg;
//    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
//    String rLines;

//    {
//        try {
//            rLines = (String) ois.readObject();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public SerializableComArg(String comArg) {
        this.comArg = comArg;
    }

    public String getComArg() {
        return comArg;
    }

    public void setDcomArg(String dcomArg) {
        this.comArg = comArg;
    }
}
