package seedu.addressbook.commands;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears address book permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_NOT_CLEARED = "Address book has not been cleared!";
    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear your address book?\n\t"
            + "Enter 'y' to confirm, 'n' to go back.";
    
    public static String CONFIRM_ANSWER = "";
    public static boolean answered = false;


    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        if(answered) { 
            if(CONFIRM_ANSWER.equalsIgnoreCase("y")){
                addressBook.clear();
                answered = false;
                return new CommandResult(MESSAGE_SUCCESS);
            }
            else {
                answered = false;
                return new CommandResult(MESSAGE_NOT_CLEARED);
            }
        }
        else {
            answered = true;
            return new CommandResult(MESSAGE_CONFIRMATION);
        }
    }
}
