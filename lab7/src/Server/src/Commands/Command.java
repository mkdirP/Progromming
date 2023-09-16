package Commands;

import Classes.Coordinates;
import Classes.Label;
import Classes.MusicBand;
import Classes.MusicGenre;
import Common.User;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Command {
    public Command() {
    }

    //    int port = 19600;
//    ServerSocket ss = null;
    ArrayList<MusicBand> musicBands = new ArrayList<>();
    ArrayList<String> unames = new ArrayList<>();
    Date Date_of_initialization = new Date();
    ArrayList<String> histories = new ArrayList<>();
    String username = "";


    private String url = "jdbc:postgresql://localhost:5432/studs";
    private String user = "postgres";
    private String password = "s336262";
    private Connection conn = null;
    public Statement stat = null;
    public PreparedStatement prep = null;

    String mode = null;
    int step = -1;
    String str = null;

    public boolean remove_lower(long numberOfParticipants) {

//        boolean bo = false;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            String sql = "DELETE from objecttable where number_of_participants<" + "'" + numberOfParticipants + "'"
                    + " and user_name=" + "'" + getUser(user1).getUserId() + "'";
            stat.executeUpdate(sql);

            ResultSet rs = stat.executeQuery("Select * from public.objecttable");
            if (!rs.next()) {
                stat.executeUpdate("DROP SEQUENCE object_id");
                stat.executeUpdate("create SEQUENCE object_id start 0 INCREMENT 1 MINVALUE 0 OWNED BY objecttable.object_id");
            }
//            bo = true;

//            ServerSocket ss = new ServerSocket(19555);
//            ss = new ServerSocket(port);
//            Socket socket = ss.accept();
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(bo);
//            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //遍历集合，获得的元素小于给定元素时，则删除该元素
        for (MusicBand mb : musicBands) {
            if (mb.getNumberOfParticipants() < numberOfParticipants) {
                if (unames.get(mb.getId()).equals(getUser(user1).getUserId())) {
                    this.remove_by_id(mb.getId());
                    try {
                        save();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
//                    bo = true;
                    return true;
                }
            }
        }



        return false;
    }

    public boolean remove_greater(long numberOfParticipants) {
//        boolean bo = false;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            String sql = "DELETE from objecttable where number_of_participants>" + "'" + numberOfParticipants + "'"
                    + " and user_name=" + "'" + getUser(user1).getUserId() + "'";
            stat.executeUpdate(sql);

            ResultSet rs = stat.executeQuery("Select * from public.objecttable");
            if (!rs.next()) {
                stat.executeUpdate("DROP SEQUENCE object_id");
                stat.executeUpdate("create SEQUENCE object_id start 0 INCREMENT 1 MINVALUE 0 OWNED BY objecttable.object_id");
            }
//            bo = true;

//            ServerSocket ss = new ServerSocket(19555);
//            ss = new ServerSocket(port);
//            Socket socket = ss.accept();
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(bo);
//            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (getUser(user1).getUserId().equals(username)) {
//            MusicBand mbmax = musicBands.stream()
//                    .max((a, b) -> (int) (a.getNumberOfParticipants() - numberOfParticipants))
//                    .get();
//            remove_by_id(mbmax.getId());
        for (MusicBand mb : musicBands) {
            if (mb.getNumberOfParticipants() > numberOfParticipants) {
                if (unames.get(mb.getId()).equals(getUser(user1).getUserId())) {
                    this.remove_by_id(mb.getId());
                    try {
                        save();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public String exec(String line) {

        if (mode == "add" || mode == "update_id") {
            switch (step) {
                case 0:
                    if (mode == "update_id") {
                        if (line.length() == 0) {
                            step += 2;
                            return "Please input Name of Label(enable null):";
                        }
                    }
                    try {
                        str += "coordinates=" + Float.parseFloat(line) + ",";
                        step++;
                        return "Please input coordinates y:";
                    } catch (Exception e) {
                        mode = null;
                        return e.getMessage();
                    }
                case 1:
                    try {
                        str += Float.parseFloat(line) + ";";
                        step++;
                        return "Please input Name of Label(enable null):";
                    } catch (Exception e) {
                        mode = null;
                        return e.getMessage();
                    }

                case 2:
                    str += "label=";
                    if (line.length() != 0) str += line + ",";
                    step++;
                    return "Please input Bands of Label:";
                case 3:
                    if (line.length() == 0) str += "N";
                    else str += line;
                    step = -1;
                    if (mode == "add") {
                        try {
                            mode = null;
                            if (add(str.split(";"))) return "Successful";
                            else return "Default";
                        } catch (Exception e) {
                            return e.getMessage();
                        }
                    } else if (mode == "update_id") {
                        try {
                            mode = null;
                            if (update_id(str.split(";"))) return "Successful";
                            else return "Default";
                        } catch (Exception e) {
                            return e.getMessage();
                        }
                    }
            }
        }

        String command;
        String[] values = null;

        if (line.indexOf(' ') != -1) {
            command = line.split(" ")[0];
            values = line.replaceFirst(command, "").trim().split(";");
        } else {
            command = line.trim();
        }
        histories.add(0, command);
        switch (command) {
            case "add":
                mode = "add";
                step = 0;
                str = line.replaceFirst(command, "").trim();
                return "Please input coordinates x:";
            case "update_id":
                mode = "update_id";
                step = 0;
                str = line.replaceFirst(command, "").trim();
                return "Please input coordinates x:";
            case "clear":
                if (clear()) return "successful clear!";
                else return "default add!";
            case "execute_script":
                try {
                    return execute_script(values[0]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case "exit":
                exit();
                break;
            case "filter_contains_name":
                return filter_contains_name(values[0]);
            case "help":
                return help(values);
            case "history":
                return history();
            case "info":
                return info();
            case "remove_all_by_genre":
                try {
                    MusicGenre musicGenre = MusicGenre.valueOf(values[0]);
                    if (remove_all_by_genre(musicGenre)) return "successful removed!";
                    else return "default!";
                } catch (Exception e) {
                    return e.getMessage();
                }
            case "remove_by_id":
                try {
                    if (remove_by_id(Integer.valueOf(values[0]))) return "success!";
                    else return "default";
                } catch (Exception e) {
                    return e.getMessage();
                }
            case "remove_lower":
                try {
                    if (remove_lower(Integer.valueOf(values[0]))) return "success!";
                    else return "default";
                } catch (Exception e) {
                    return e.getMessage();
                }
            case "remove_greater":
                try {
                    if (remove_greater(Integer.valueOf(values[0]))) return "success!";
                    else return "default";
                } catch (Exception e) {
                    return e.getMessage();
                }
            case "save":
                try {
                    if (save()) return "successful!";
                    else return "default";
                } catch (IOException e) {
                    return "File not found";
                }
            case "show":
                return show();
            case "filter_starts_with_name":
                return filter_starts_with_name(values[0]);
            default:
                return "Command not found.";
        }
        return command;
    }

    User user1 = null;

    public User getUser(User u) {
        String id = u.getUserId();
        String pwd = u.getPasswd();
        user1 = new User();
        user1.setUserId(id);
        user1.setPasswd(pwd);
        return user1;
    }

    public boolean add(String[] values) {
        Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        String name = null; //Поле не может быть null, Строка не может быть пустой
        Coordinates coordinates = null; //Поле не может быть null
        Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        long numberOfParticipants = 0; //Значение поля должно быть больше 0
        String description = null; //Поле может быть null
        MusicGenre genre = null; //Поле может быть null
        Label label = null; //Поле может быть null


        for (String str : values) {
            if (str == null) {
                continue;
            }
            if (str.indexOf("=") == -1) {
                continue;
            } else {
                String var = str.split("=")[1];
                switch (str.split("=")[0]) {
                    case "name":
                        name = var;
                        break;
                    case "coordinates":
                        try {
                            Float x = Float.valueOf(var.split(",")[0]);
                            float y = Float.parseFloat(var.split(",")[1]);
                            coordinates = new Coordinates();
                            coordinates.set(x, y);
                        } catch (Exception e) {
                            mode = null;
                            throw new RuntimeException(e);
                        }
                        break;
                    case "numberOfParticipants":
                        try {
                            numberOfParticipants = Long.parseLong(var);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "description":
                        description = var;
                        break;
                    case "genre":
                        try {
                            genre = MusicGenre.valueOf(var);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "label":
                        String labelName = null;
                        Long labelBands;
                        if (var.indexOf(",") == -1) {
                            if (var.equals("N")) break;
                            try {
                                labelBands = Long.valueOf(var);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            if (var.split(",")[1].equals("N")) break;
                            labelName = var.split(",")[0];
                            labelBands = Long.valueOf(var.split(",")[1]);
                        }
                        label = new Label();
                        label.set(labelName, labelBands);
                        break;
                }
            }
        }
        creationDate = new Date();
        id = musicBands.size();
        MusicBand musicBand = new MusicBand();

        if (musicBand.set(id, name, coordinates, creationDate, numberOfParticipants, description, genre, label)) {

            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(url, user, password);
                prep = conn.prepareStatement("INSERT INTO studs.public.objecttable(object_name,number_of_participants,description,genre,creationdate,coordinates,\n" +
                        "label,object_id,user_name) VALUES (?,?,?,?,?,?,?,nextval('object_id'),?);");
                //专门执行DML语句的，返回值是“影响数据库中的记录条数”
                prep.setObject(1, musicBand.getName());
                prep.setObject(2, musicBand.getNumberOfParticipants());
                prep.setObject(3, musicBand.getDescription());
                prep.setObject(4, musicBand.getGenre().toString());
                prep.setObject(5, musicBand.getCreationDate().toString());
                prep.setObject(6, musicBand.getCoordinates().toString());
                prep.setObject(7, musicBand.getLabel().toString());
                prep.setObject(8, getUser(user1).getUserId());

                prep.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            musicBands.add(musicBand);

            try {
                save();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return true;

        }
        return false;
    }

    /**
     * @param values
     * @return
     */
    public boolean update_id(String[] values) {
        Integer id = -1; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        String name = null; //Поле не может быть null, Строка не может быть пустой
        Coordinates coordinates = null; //Поле не может быть null
        Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        long numberOfParticipants = -1; //Значение поля должно быть больше 0
        String description = null; //Поле может быть null
        MusicGenre genre = null; //Поле может быть null
        Label label = null; //Поле может быть null

        for (String str : values) {
            if (str == null) {
                continue;
            }
            if (!str.contains("=")) {
                continue;
            } else {
                String var = str.split("=")[1];
                switch (str.split("=")[0]) {
                    case "name":
                        name = var;
                        break;
                    case "coordinates":
                        Float x = Float.valueOf(var.split(",")[0]);
                        float y = Float.parseFloat(var.split(",")[1]);
                        coordinates = new Coordinates();
                        try {
                            coordinates.set(x, y);
                        } catch (Exception e) {
                            mode = null;
                            throw new RuntimeException(e);
                        }
                        break;
                    case "numberOfParticipants":
                        numberOfParticipants = Long.parseLong(var);
                        break;
                    case "description":
                        description = var;
                        break;
                    case "genre":
                        genre = MusicGenre.valueOf(var);
                        break; //try catch
                    case "label":

                        String labelName = null;
                        Long labelBands;
                        if (var.indexOf(",") == -1) {
                            if (var.equals("N")) break;
                            labelBands = Long.valueOf(var);
                        } else {
                            if (var.split(",")[1].equals("N")) break;
                            labelName = var.split(",")[0];
                            labelBands = Long.valueOf(var.split(",")[1]);
                        }
                        label = new Label();
                        label.set(labelName, labelBands);
                        break;
                    case "id":
                        id = Integer.valueOf(var);
                }
            }
        }
        if (id == -1) return false;
        MusicBand musicBand = new MusicBand();
        musicBand.setLabel(label);
        musicBand.setCoordinates(coordinates);
        musicBand.setName(name);
        musicBand.setId(id);

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();

            String sql = "update objecttable set object_name=" + "'" + musicBand.getName() + "'" + ", coordinates=" + "'" + musicBand.getCoordinates().toString() + "'" +
                    ", label=" + "'" + musicBand.getLabel().toString() + "'" + " where objecttable.object_id=" + "'" + musicBand.getId() + "'" +
                    " and objecttabel.user_name=" + "'" + getUser(user1).getUserId();
            stat.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (getUser(user1).getUserId().equals(username)) {
            for (MusicBand mb : musicBands) {
                if (mb.getId() == id) {
                    if (name != null) mb.setName(name);
                    if (coordinates != null) mb.setCoordinates(coordinates);
                    if (numberOfParticipants != -1) mb.setNumberOfParticipants(numberOfParticipants);
                    if (description != null) mb.setDescription(description);
                    if (genre != null) mb.setGenre(genre);
                    if (label != null) mb.setLabel(label);
                }
            }
            try {
                save();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    public boolean clear() {
        //清空集合时也应该清除xml文件中元素？

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            String sql = "DELETE FROM public.objecttable";
            String sql1 = "DROP SEQUENCE object_id";
            String sql2 = "create SEQUENCE object_id start 0 INCREMENT 1 MINVALUE 0 OWNED BY objecttable.object_id";

            stat.executeUpdate(sql);
            stat.executeUpdate(sql1);
            stat.executeUpdate(sql2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        musicBands.clear();
        try {
            save();
        } catch (IOException e) {
            System.out.println("clear");
            throw new RuntimeException(e);
        }
        return true;
    }

    public String execute_script(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        StringBuilder ret = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            String x = "> " + line + "\n" + exec(line) + "\n\n";
            ret.append(x);
        }
        br.close();
        return ret.toString();
    }

    public String help(String[] values) {
        if (values == null) {
            return "help : вывести справку по доступным командам\n" +
                    "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                    "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                    "add {element} : добавить новый элемент в коллекцию\n" +
                    "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                    "remove_by_id id : удалить элемент из коллекции по его id\n" +
                    "clear : очистить коллекцию\n" +
                    "save : сохранить коллекцию в файл\n" +
                    "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                    "exit : завершить программу (без сохранения в файл)\n" +
                    "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный \n" +
                    "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                    "history : вывести последние 6 команд (без их аргументов)\n" +
                    "remove_all_by_genre genre : удалить из коллекции все элементы, значение поля genre которого эквивалентно заданному\n" +
                    "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку\n" +
                    "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки";
        } else {
            switch (values[0]) {
                case "add":
                    return "Example: " +
                            "add name=Lubeh;numberOfParticipants=7;description=Their music is a mix of rock, Russian folk and military music;genre=POST_ROCK;\n" +
                            "arguments (\"-\" means must input, \"*\" means optional argument):\n" +
                            "       - name: Input bands name here\n" +
                            "       - coordinates=x,y: Input location\n" +
                            "       - numberOfParticipants: Input how many people in bands\n" +
                            "       * description: input description of bands\n" +
                            "       * genre: RAP,PSYCHEDELIC_CLOUD_RAP,SOUL,POST_ROCK,PUNK_ROCK;\n" +
                            "       * label=[name,]bands: Input label name and label bands";
                case "clear":
                    return "clear - clear all elements";
                case "execute_script":
                    return "execute_script file - Executes a line-by-line file with recursion protection";
                case "exit":
                    return "exit - exit without saving.";
                case "filter_contains_name":
                    return "filter_contains_name name - outputs items whose name field values contain the specified substring.";
                case "history":
                    return "history - print the last 6 commands (without their arguments).";
                case "info":
                    return "info - displays information about the collection.";
                case "remove_all_by_genre":
                    return "remove_all_by_genre - remove from the collection all elements whose genre field value is equivalent to the given one.";
                case "remove_by_id":
                    return "remove_by_id id - remove an item from the collection by its id. You can specify more than one id.\n" +
                            "Example: remove_by_id 1;2;3\n" +
                            "Then the command to remove elements from 1, 2 and 3 ID.";
                case "remove_lower":
                    return "remove_lower - remove from the collection all elements smaller than the given one.\n" +
                            "arguments:\n" +
                            "       - name: Input bands name here\n" +
                            "       - coordinates=x,y: Input location\n" +
                            "       - numberOfParticipants: Input how many people in bands\n" +
                            "       - description: input description of bands\n" +
                            "       - genre: RAP,PSYCHEDELIC_CLOUD_RAP,SOUL,POST_ROCK,PUNK_ROCK;\n" +
                            "       - label=[name,]bands: Input label name and label bands";
                case "remove_greater":
                    return "remove_greater - remove from the collection all elements greater than the given.\n" +
                            "arguments:\n" +
                            "       - name: Input bands name here\n" +
                            "       - coordinates=x,y: Input location\n" +
                            "       - numberOfParticipants: Input how many people in bands\n" +
                            "       - description: input description of bands\n" +
                            "       - genre: RAP,PSYCHEDELIC_CLOUD_RAP,SOUL,POST_ROCK,PUNK_ROCK;\n" +
                            "       - label=[name,]bands: Input label name and label bands";
                case "save":
                    return "save - simply saves the collection to a file.\n";
                case "show":
                    return "show - outputs to the standard output stream all elements of the collection in string representation.";
                case "update_id":
                    return "update id name x y numberOfParticipants - standard command with primitive data entry.\n" +
                            "arguments:\n" +
                            "       - id: Please enter the id whose field value needs to be updated." +
                            "       - name: Input bands name here\n" +
                            "       - coordinates=x,y: Input location\n" +
                            "       - numberOfParticipants: Input how many people in bands\n" +
                            "       - description: input description of bands\n" +
                            "       - genre: RAP,PSYCHEDELIC_CLOUD_RAP,SOUL,POST_ROCK,PUNK_ROCK;\n" +
                            "       - label=[name,]bands: Input label name and label bands";
                case "filter_starts_with_name":
                    return "filter_starts_with_name - display elements whose \"name\" field value starts with the given substring.\n" +
                            "argument:\n" +
                            "       - name: Input substring";
                default:
                    return null;
            }
        }
    }

    public void exit() {
        System.out.println("Disconnect and end the program");
        System.exit(0);
    }

    // 读取xml文件
    public void domXml() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse("D:\\A_itmo\\iProgram\\lab7s\\src\\Server\\musicBands.xml");
            NodeList mbList = document.getElementsByTagName("MusicBand");    //节点集

            int mbCnt = mbList.getLength();

//            String[] values = new String[8];
            ArrayList<String> values = new ArrayList<>();
            int m = 0;

            for (int i = 0; i < mbCnt; i++) {
                Node mb = mbList.item(i);
                NamedNodeMap attrs = mb.getAttributes();
                for (int j = 0; j < attrs.getLength(); j++) {
                    Node attr = attrs.item(j);
                    System.err.println(attr.getNodeName() + "---" + attr.getNodeValue());//id

                }
                NodeList childNodes = mb.getChildNodes();
                String str = "";
                for (int k = 0; k < childNodes.getLength(); k++) {
                    if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        str += new String(childNodes.item(k).getNodeName() + "=" + childNodes.item(k).getTextContent() + ";");
//                        values[m] = str;
                    }
                }
//                System.out.println(str);
                values.add(str);
                m++;
            }
//            System.out.println(values.size());
            if (mbCnt != 0 && musicBands.size() == 0) {
                added(values);
            }

//            System.out.println("kkk");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public void added(ArrayList<String> values) throws ParseException {
//        MusicBand musicBand = new MusicBand();
        Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        String name = null; //Поле не может быть null, Строка не может быть пустой
        Coordinates coordinates = null; //Поле не может быть null
        Date creationDate = null; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        long numberOfParticipants = 0; //Значение поля должно быть больше 0
        String description = null; //Поле может быть null
        MusicGenre genre = null; //Поле может быть null
        Label label = null; //Поле может быть null
        String userName = null;


        for (String str : values) {
            String[] results = str.split(";");
            for (int flag = 1; flag < results.length; flag++) {

                String var = (str.split(";")[flag]).split("=")[1];
                switch ((str.split(";")[flag]).split("=")[0]) {
                    case "Name":
                        name = var;
                        break;
                    case "Coordinates":
                        try {
                            Float x = Float.valueOf(var.split(",")[0]);
                            float y = Float.parseFloat(var.split(",")[1]);
                            coordinates = new Coordinates();
                            coordinates.set(x, y);
                        } catch (Exception e) {
                            mode = null;
                            throw new RuntimeException(e);
                        }
                        break;
                    case "CreationDate":
                        String s = var;
                        SimpleDateFormat forMatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                        Date date = forMatter.parse(s);
                        creationDate = date;
                        break;
                    case "NumberOfParticipants":
                        try {
                            numberOfParticipants = Long.parseLong(var);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Description":
                        description = var;
                        break;
                    case "Genre":
                        try {
                            genre = MusicGenre.valueOf(var);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Label":
                        String labelName = null;
                        Long labelBands;
                        if (var.indexOf(",") == -1) {
                            if (var.equals("N")) break;
                            try {
                                labelBands = Long.valueOf(var);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            if (var.split(",")[1].equals("N")) break;
                            labelName = var.split(",")[0];
                            labelBands = Long.valueOf(var.split(",")[1]);
                        }
                        label = new Label();
                        label.set(labelName, labelBands);
                        break;
                    case "UserName":
                        userName = var;
                        unames.add(userName);
                        break;
                }
            }

            username = userName;
            id = musicBands.size();
            MusicBand musicBand = new MusicBand();

            musicBand.set(id, name, coordinates, creationDate, numberOfParticipants, description, genre, label);
            musicBands.add(musicBand);
        }
    }

    public boolean save() throws IOException {



        String xml = "<?xml version=\"1.0\"?>\n" +
                "<MusicBands>\n";
        if (musicBands.size()!=0) {
            if (getUser(user1).getUserId().equals(username)) {

                for (MusicBand mb : musicBands) {
                    xml += "    <MusicBand>\n" +
                            "        <Id>" + mb.getId() + "</Id>\n" +
                            "        <Name>" + mb.getName() + "</Name>\n" +
                            "        <Coordinates>" + mb.getCoordinates() + "</Coordinates>\n" +
                            "        <CreationDate>" + mb.getCreationDate() + "</CreationDate>\n" +
                            "        <NumberOfParticipants>" + mb.getNumberOfParticipants() + "</NumberOfParticipants>\n" +
                            "        <Description>" + mb.getDescription() + "</Description>\n" +
                            "        <Genre>" + mb.getGenre() + "</Genre>\n" +
                            "        <Label>" + mb.getLabel() + "</Label>\n" +
                            "        <UserName>" + username + "</UserName>\n" +
                            "    </MusicBand>\n";
                }
                xml += "</MusicBands>";
            } else {
                for (int i = 0; i < musicBands.size() - 1; i++) {
                    xml += "    <MusicBand>\n" +
                            "        <Id>" + musicBands.get(i).getId() + "</Id>\n" +
                            "        <Name>" + musicBands.get(i).getName() + "</Name>\n" +
                            "        <Coordinates>" + musicBands.get(i).getCoordinates() + "</Coordinates>\n" +
                            "        <CreationDate>" + musicBands.get(i).getCreationDate() + "</CreationDate>\n" +
                            "        <NumberOfParticipants>" + musicBands.get(i).getNumberOfParticipants() + "</NumberOfParticipants>\n" +
                            "        <Description>" + musicBands.get(i).getDescription() + "</Description>\n" +
                            "        <Genre>" + musicBands.get(i).getGenre() + "</Genre>\n" +
                            "        <Label>" + musicBands.get(i).getLabel() + "</Label>\n" +
                            "        <UserName>" + username + "</UserName>\n" +
                            "    </MusicBand>\n";
                }
                MusicBand mb = musicBands.get(musicBands.size() - 1);
                xml += "    <MusicBand>\n" +
                        "        <Id>" + mb.getId() + "</Id>\n" +
                        "        <Name>" + mb.getName() + "</Name>\n" +
                        "        <Coordinates>" + mb.getCoordinates() + "</Coordinates>\n" +
                        "        <CreationDate>" + mb.getCreationDate() + "</CreationDate>\n" +
                        "        <NumberOfParticipants>" + mb.getNumberOfParticipants() + "</NumberOfParticipants>\n" +
                        "        <Description>" + mb.getDescription() + "</Description>\n" +
                        "        <Genre>" + mb.getGenre() + "</Genre>\n" +
                        "        <Label>" + mb.getLabel() + "</Label>\n" +
                        "        <UserName>" + getUser(user1).getUserId() + "</UserName>\n" +
                        "    </MusicBand>\n";
                xml += "</MusicBands>";
            }
        }else {
            xml += "</MusicBands>";
        }

        OutputStream f = new FileOutputStream("D:\\A_itmo\\iProgram\\lab7s\\src\\Server\\musicBands.xml");
        OutputStreamWriter writer = new OutputStreamWriter(f, "UTF-8");
        writer.append(xml);
        writer.close();
        f.close();

        return true;
    }

    public String show() {
        domXml();
        String ret = "";
        for (MusicBand mb : musicBands) {
            ret += showElement(mb);
        }

        if (musicBands.size() == 0) ret = "Collection is empty!";

        return ret;

//        String str1 = "";
//        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection(url, user, password);
//            stat = conn.createStatement();
//
//            String sql = "select * from public.objecttable ";
//            ResultSet rs = stat.executeQuery(sql);
//
//            while (rs.next()) {
//                int object_id = rs.getInt("object_id");
//                String name = rs.getString("object_name");
//                int number_of_participants = rs.getInt("number_of_participants");
//                String description = rs.getString("description");
//                String genre = rs.getString("genre");
//                String creationdate = rs.getString("creationdate");
//                String coordinates = rs.getString("coordinates");
//                String label = rs.getString("label");
//
//                str1 += "Id - " + object_id +
//                        "\n    Name:" + name +
//                        "\n    Coordinates:" + coordinates +
//                        "\n    CreationDate:" + creationdate +
//                        "\n    NumberOfParticipants:" + number_of_participants +
//                        "\n    Description:" + description +
//                        "\n    Genre:" + genre +
//                        "\n    Label:" + label + "\n";
//
//            }


//            //将str1序列化后发给客户端
////            ServerSocket ss = new ServerSocket(19551);
//            ss = new ServerSocket(port);
//            Socket socket = ss.accept();
//
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(str1);
//
//            ss.close();
//            rs.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return str1;
    }

    private String showElement(MusicBand mb) {

        return "Id - " + mb.getId() +
                "\n    Name:" + mb.getName() +
                "\n    Coordinates:" + mb.getCoordinates() +
                "\n    CreationDate:" + mb.getCreationDate() +
                "\n    NumberOfParticipants:" + mb.getNumberOfParticipants() +
                "\n    Description:" + mb.getDescription() +
                "\n    Genre:" + mb.getGenre() +
                "\n    Label:" + mb.getLabel() + "\n";

//        String str1 = "";
//        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection(url, user, password);
//            stat = conn.createStatement();
//
//            String sql = "select * from public.objecttable ";
//            ResultSet rs = stat.executeQuery(sql);
//
//            while (rs.next()) {
//                int object_id = rs.getInt("object_id");
//                String name = rs.getString("object_name");
//                int number_of_participants = rs.getInt("number_of_participants");
//                String description = rs.getString("description");
//                String genre = rs.getString("genre");
//                String creationdate = rs.getString("creationdate");
//                String coordinates = rs.getString("coordinates");
//                String label = rs.getString("label");
//
//                str1 = "Id - " + object_id +
//                        "\n    Name:" + name +
//                        "\n    Coordinates:" + number_of_participants +
//                        "\n    CreationDate:" + creationdate +
//                        "\n    NumberOfParticipants:" + number_of_participants +
//                        "\n    Description:" + description +
//                        "\n    Genre:" + genre +
//                        "\n    Label:" + label + "\n";
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return str1;

    }

    public String info() {
        return "Collection type: " + musicBands.getClass().getTypeName() +
                "\nDate of initialization: " + Date_of_initialization +
                "\nNumber of elements: " + musicBands.size();
//        String str2 = "";
//        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection(url, user, password);
//            stat = conn.createStatement();
//
//            String sql = "SELECT count(*) FROM objecttable ";
//            ResultSet rs = stat.executeQuery(sql);
//            while (rs.next()) {
//                int num = rs.getInt("count");
//                str2 = "Collection type: " + musicBands.getClass().getTypeName() +
//                        "\nDate of initialization: " + Date_of_initialization +
//                        "\nNumber of elements: " + num;
//            }
//            ServerSocket ss = new ServerSocket(19552);
//            ss = new ServerSocket(port);
//            Socket socket = ss.accept();
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(str2);
//            ss.close();
//            rs.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return str2;
    }

    public boolean remove_by_id(Integer id) {

        boolean b = false;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            prep = conn.prepareStatement("delete from public.objecttable where object_id=? and user_name=?");
            prep.setObject(1, id);
            prep.setObject(2, getUser(user1).getUserId());
            prep.execute();

            ResultSet rs = stat.executeQuery("Select * from public.objecttable");
            if (!rs.next()) {
                stat.executeUpdate("DROP SEQUENCE object_id");
                stat.executeUpdate("create SEQUENCE object_id start 0 INCREMENT 1 MINVALUE 0 OWNED BY objecttable.object_id");
            }
            b = true;

//            ServerSocket ss = new ServerSocket(19553);
//            ss = new ServerSocket(port);
//            Socket socket = ss.accept();
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(b);
//            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser(user1).getUserId().equals(unames.get(id))) {
            int i = 0;
            boolean exist = false;
            for (; i < musicBands.size(); i++) {
                if (musicBands.get(i).getId() == id) {
                    exist = true;
                    break;
                }
            }
            if (!exist) return false;
            musicBands.remove(i);
            try {
                save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return b;

    }

    public boolean remove_all_by_genre(MusicGenre genre) {
        ArrayList<Integer> ids = new ArrayList<>();
        boolean b = false;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            String sql = "DELETE from public.objecttable where genre=" + "'" + genre + "'"
                    + " and user_name=" + "'" + getUser(user1).getUserId() + "'";
            stat.executeUpdate(sql);


            ResultSet rs = stat.executeQuery("Select * from public.objecttable");
            if (!rs.next()) {
                stat.executeUpdate("DROP SEQUENCE object_id");
                stat.executeUpdate("create SEQUENCE object_id start 0 INCREMENT 1 MINVALUE 0 OWNED BY objecttable.object_id");
            }
            b = true;

//            ServerSocket ss = new ServerSocket(19554);
//            ss = new ServerSocket(port);
//            Socket socket = ss.accept();
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(b);
//            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (MusicBand musicBand : musicBands) {
            if (musicBand.getGenre() == genre) {
                ids.add(musicBand.getId());
            }
        }
        for (Integer i : ids) {
            if (unames.get(i).equals(getUser(user1).getUserId())) {
                remove_by_id(i);
                b = true;
            }
        }
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return b;
    }


    public String filter_starts_with_name(String subName) {
        StringBuilder ret = new StringBuilder();
        for (MusicBand mb : musicBands) {
            if (mb.getName().startsWith(subName))
                ret.append(showElement(mb));
        }
        return ret.toString();
    }

    public String filter_contains_name(String subName) {
        StringBuilder ret = new StringBuilder();
        for (MusicBand mb : musicBands) {
            if (mb.getName().indexOf(subName) != -1)
                ret.append(showElement(mb));
        }
        return ret.toString();
    }

    public String history() {
        String ret = "";
        int n = histories.size() < 6 ? histories.size() : 6;
        for (int i = 0; i < n; i++)
            ret += histories.get(i) + "/";
        return ret;
    }


}

