import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;
import org.json.JSONTokener;

public class MainClass {

    public static final Scanner scanner = new Scanner(System.in);

    static IAuthService authService = new FileAuthService();

    static void logIn(Person person) throws FileNotFoundException {
        while (true) {
            System.out.println("Choose one option: ");
            System.out.println("1 - Add new note 2 - Change note 3 - Show all notes 4 - Delete notes 5 - Log out 6 - Change password 7 - Delete account ");
            String chosenOption = scanner.next();
            if (chosenOption.equals("1")) {
                System.out.println("Create new note: ");
                scanner.nextLine();
                String typedNote = scanner.nextLine();
                Note note = new Note(typedNote);
                person.addNote(note);
            } else if (chosenOption.equals("2")) {
                while (true) {
                    if (person.getNotesSize() == 0) {
                        System.out.println("You don't have any notes");
                        break;
                    }
                    person.printNotes();
                    System.out.println("Type the number of note you want to change. Chosen note's current content will be deleted. Type \"0\" if you want to go back. ");
                    int typedNumber = scanner.nextInt();
                    if (typedNumber - 1 < person.getNotesSize() && typedNumber != 0) {
                        System.out.println("Create new note: ");
                        scanner.nextLine();
                        String typedNote = scanner.nextLine();
                        person.setNote(typedNumber - 1, typedNote);
                        System.out.println("Note successfully changed.");
                    } else {
                        if (typedNumber == 0) {
                            break;
                        } else {
                            System.out.println("Note number " + typedNumber + " doesn't exist.");
                        }
                    }
                }
            } else if (chosenOption.equals("3")) {
                if (person.getNotesSize() == 0) {
                    System.out.println("You don't have any notes");
                    continue;
                }
                person.printNotes();
            } else if (chosenOption.equals("4")) {
                while (true) {
                    if (person.getNotesSize() == 0) {
                        System.out.println("You don't have any notes.");
                        break;
                    } else {
                        person.printNotes();
                        System.out.println("Type the number of note you want to delete. Type \"0\" if you want to go back. ");
                        int typedNumber = scanner.nextInt();
                        if ((typedNumber <= person.getNotesSize()) && (typedNumber != 0)) {
                            person.deleteNote(typedNumber);
                            System.out.println("Note successfully deleted.");
                        } else {
                            if (typedNumber == 0) {
                                break;
                            } else {
                                System.out.println("Note number " + typedNumber + " doesn't exist.");
                            }
                        }
                    }
                }
            } else if (chosenOption.equals("5")) {
                authService.logout();
                break;
            } else if (chosenOption.equals("6")) {
                while (true) {
                    System.out.println("Type your current password. Type \"exit\" if you want to go back.");
                    String typedPassword = scanner.next();
                    if (person.getPassword().equals(typedPassword)) {
                        System.out.println("Type new password: ");
                        typedPassword = scanner.next();
                        person.setPassword(typedPassword);
                        System.out.println("Password successfully changed.");
                        break;
                    } else {
                        if (typedPassword.equals("exit")) {
                            break;
                        } else {
                            System.out.println("Try again.");
                        }
                    }
                }
            } else if (chosenOption.equals("7")) {
                while (true) {
                    System.out.println("Type your password to delete account. Type \"exit\" if you want to go back.");
                    String typedAnswer = scanner.next();
                    if (typedAnswer.equals(person.getPassword())) {
                        break;
                    }
                    if (typedAnswer.equals("exit")) {
                        break;
                    } else {
                        System.out.println("Password is incorrect");
                        continue;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        while (true) {
            System.out.println("Choose one option: ");
            System.out.println("1 - REGISTER 2 - LOGIN 3 - EXIT");
            String chosenOption = scanner.next();
            if (chosenOption.equals("1")) {
                System.out.println("Type your nickname: ");
                String nickname = scanner.next();
                String password;
                while (true) {
                    System.out.println("Type your password: ");
                    password = scanner.next();
                    if (password.length() < 5) {
                        System.out.println("Typed password is too short. Try again.");
                    } else {
                        break;
                    }
                }
                Boolean registerValue = authService.register(nickname, password);
                if (registerValue){
                    System.out.println("Registration completed, you can log in or create another account.");
                } else {
                    System.out.println("This nickname is already taken. Try again. ");
                }
            } else if (chosenOption.equals("2")) {
                System.out.println("Type your nickname: ");
                String nickname = scanner.next();
                System.out.println("Type your password: ");
                String password = scanner.next();
                authService.login(nickname, password);
                Person person = authService.getPerson();
                if (person != null) {
                    logIn(person);
                } else {
                    System.out.println("Try again. ");
                }
            } else if (chosenOption.equals("3")) {
                System.exit(0);
            } else {
                System.out.println("Try again. ");
            }
        }
    }
}