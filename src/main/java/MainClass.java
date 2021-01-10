import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {
    public static final Scanner scanner = new Scanner(System.in);
    static IAuthService authService = new FileAuthService();

    private static final String ADD_NEW_NOTE = "1";
    private static final String CHANGE_NOTE = "2";
    private static final String SHOW_ALL_NOTES = "3";
    private static final String DELETE_NOTES = "4";
    private static final String CHANGE_PASSWORD = "5";
    private static final String DELETE_ACCOUNT = "6";
    private static final String LOG_OUT = "7";

    static void logIn(Person person) throws FileNotFoundException {
        while (true) {
            System.out.println("Choose one option: ");
            System.out.println("1 - Add new note\n2 - Change note\n3 - Show all notes\n4 - Delete notes\n5 - Change password\n6 - Delete account\n7 - Log out\n");
            String chosenOption = scanner.next();
            if (chosenOption.equals(ADD_NEW_NOTE)) {
                System.out.println("Create new note: ");
                scanner.nextLine();
                String typedNote = scanner.nextLine();
                Note note = new Note(typedNote);
                person.addNote(note);
            } else if (chosenOption.equals(CHANGE_NOTE)) {
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
            } else if (chosenOption.equals(SHOW_ALL_NOTES)) {
                if (person.getNotesSize() == 0) {
                    System.out.println("You don't have any notes");
                    continue;
                }
                person.printNotes();
            } else if (chosenOption.equals(DELETE_NOTES)) {
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
            } else if (chosenOption.equals(LOG_OUT)) {
                authService.logout();
                break;
            } else if (chosenOption.equals(CHANGE_PASSWORD)) {
                while (true) {
                    System.out.println("Type your current password. Type \"exit\" if you want to go back.");
                    String typedPassword = scanner.next();
                    if (person.getPassword().equals(typedPassword)) {
                        System.out.println("Type your new password: ");
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
            } else if (chosenOption.equals(DELETE_ACCOUNT)) {
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
            System.out.println("1 - REGISTER\n2 - LOGIN\n3 - EXIT\n");
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