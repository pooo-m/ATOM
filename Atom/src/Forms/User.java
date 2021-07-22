package Forms;

public class User {
    private String name;
    private String password;
    private int id;
    private Boolean dev;
    User(String name, int id){
    this.name = name;

    this.id = id;


    }

    void setName(String name){
        this.name =name;
    }
    void setPassword(String pass){
        this.password =pass;
    }

    String getName(){
        return name;
    }
    String getPassword(){
        return password;
    }


    int getId(){
        return id;
    }
    void setId(int id){    this.id = id;  }
}
