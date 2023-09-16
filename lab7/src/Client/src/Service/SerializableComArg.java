package Service;

import Classes.MusicBand;

import java.io.Serializable;

/**
 * Request
 */

public class SerializableComArg implements Serializable {
    private static final long serialVersionUID = 14L;

    // 输入的命令及其参数的序列化。在服务器端反序列化后执行
    private String comArg;
    // 在服务器端用save保存，服务器端获取客户端所输入的对象的序列化文件并反序列化后添加到集合中
    MusicBand musicBand;

    @Override
    public String toString() {
        return comArg;
    }

    public SerializableComArg(String comArg){
        this.comArg = comArg;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    public String getComArg() {
        return comArg;
    }

    public void setComArg(String comArg) {
        this.comArg = comArg;
    }
}
