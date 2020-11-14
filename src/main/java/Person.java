import java.util.ArrayList;
import java.util.List;

public class Person {
    private String nickname;

    private String password;

    List<Note> notes = new ArrayList();

    Person(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    void setNickname(String nickname) {
        this.nickname = nickname;
    }

    void setPassword(String password) {
        this.password = password;
    }

    void addNote(Note note) {
        notes.add(note);
    }

    String getNickname() {
        return nickname;
    }

    String getPassword() {
        return password;
    }

    void printNotes() {
        for (int i = 0; i < notes.size(); i++) {
            System.out.println("Note number " + (i + 1) + ": ");
            System.out.println(notes.get(i).getText());
        }
    }

    List<Note> getNotes(){
        return notes;
    }


    void setNote(int chosenNote, String newText) {
        notes.get(chosenNote).setText(newText);
    }

    int getNotesSize() {
        return notes.size();
    }

    void deleteNote(int noteNumber) {
        notes.remove(noteNumber - 1);
    }
}
