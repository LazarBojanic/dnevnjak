package rs.raf.dnevnjak.model;


import java.io.Serializable;

public class ServiceUser implements Serializable {
    private Integer id;
    private String email;
    private String username;
    private String pass;

    public ServiceUser() {

    }

    public ServiceUser(Integer id, String email, String username, String pass) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.pass = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "ServiceUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}