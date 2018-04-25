package b12app.vyom.com.shopoholic.model;

import java.io.Serializable;

public class User implements Serializable {

    /**
     * msg : success
     * id : 1217
     * firstname : aamir
     * lastname : husain
     * email : aa@gmail.com
     * mobile : 55565454
     * appapikey  : 7057bc8168b477b9420aeca3fda3c868
     */


    private String firstname;
    private String lastname;
    private String email;
    private String mobile;


    public User(String firstname, String lastname, String email, String mobile)  {

        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobile = mobile;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
