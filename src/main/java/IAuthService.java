import java.io.FileNotFoundException;

public interface IAuthService {
    void login(String nickname, String password) throws FileNotFoundException;

    boolean register(String nickname, String password) throws FileNotFoundException;

    void logout() throws FileNotFoundException;

    Person getPerson();
}
