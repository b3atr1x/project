import java.util.ArrayList;
import java.util.List;

public class AuthService implements IAuthService {
    private List<Person> people = new ArrayList<>();

    @Override
    public void login(String nickname, String password) {
        for (Person person : people) {
            if (person.getNickname().equals(nickname) && person.getPassword().equals(password)) {
            }
        }
    }

    @Override
    public boolean register(String nickname, String password) {
        for (Person value : people) {
            if (value.getNickname().equals(nickname)) {
                System.out.println("This nickname is already taken. Try again. ");
                return false;
            }
        }
        Person person = new Person(nickname, password);
        people.add(person);

        return true;
    }

    @Override
    public void logout() {}

    @Override
    public Person getPerson() {
        return null;
    }
}
