package Users;


import com.example.fit_4_life.User;

public class Member extends User {

    private String gymClass;

    public Member (int id, String username, String password, String gymClass) {
        super (id, username, password);
        this.gymClass = gymClass;
    }

    public Member() {

    }

    public String getGymClass() {
        return gymClass;
    }

    public void setGymClass(String gymClass) {
        this.gymClass = gymClass;
    }

}
