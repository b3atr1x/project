import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

public class FileAuthService implements IAuthService {
    File dir = new File("users");
    private Person person;

    public Person getPerson() {
        return person;
    }

    @Override
    public void login(String nickname, String password) throws FileNotFoundException {
        if (!dir.exists()) {
            return;
        } else {
            File file = new File(dir.getName(), nickname);
            if (file.exists()) {
                JSONTokener jsonTokener = new JSONTokener(new FileInputStream(file));
                JSONObject jsonObject = new JSONObject(jsonTokener);
                if (jsonObject.getString("password").equals(password)) {
                    person = new Person(nickname, password);
                }
            }
        }
    }

    @Override
    public boolean register(String nickname, String password) throws FileNotFoundException {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File[] files = dir.listFiles();
        for (File value : files) {
            if (value.getName().equals(nickname)) {
                return false;
            }
        }
        JSONObject user = new JSONObject();
        user.put("password", password);
        File file = new File(dir.getName(), nickname);
        PrintWriter printwriter = new PrintWriter(file);
        printwriter.write(user.toString(4));
        printwriter.close();
        return true;
    }


    @Override
    public void logout() throws FileNotFoundException {
        JSONArray jsonArray = new JSONArray();
        File file = new File(dir.getName(), person.getNickname());
        if (file.exists()) {
            file.delete();
            JSONObject user = new JSONObject();
            user.put("password", person.getPassword());
            for (int i = 0; i < person.getNotesSize(); i++) {
                JSONObject note = new JSONObject();
                note.put("note", person.getNotes().get(i).getText());
                jsonArray.put(note);
                System.out.println("sdknfdskjn");
            }
            user.put("notes", jsonArray);
            PrintWriter printwriter = new PrintWriter(file);
            printwriter.write(user.toString(4));
            printwriter.close();
            person = null;
        }
    }


}
