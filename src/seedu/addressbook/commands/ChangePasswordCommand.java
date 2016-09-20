package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;
import seedu.addressbook.logic.Logic;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class ChangePasswordCommand extends Command {

    public static final String COMMAND_WORD = "changepw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Changes password of the address book. "
            + "Parameters: NAME OLDPASSWORD NEWPASSWORD...\n\t"
            + "Example: " + COMMAND_WORD
            + " 12345678 qwerty";

    public static final String MESSAGE_SUCCESS = "Password Changed";
    public static final String MESSAGE_FAILURE = "Wrong Old Password";
    
    public static boolean successfulChange = false;

    public ChangePasswordCommand(Logic logic, String oldPassword, String newPassword) {
        successfulChange = logic.ChangePassword(oldPassword, newPassword);
    }

    @Override
    public CommandResult execute() {
    	if (successfulChange) {
    		return new CommandResult(MESSAGE_SUCCESS);
    	} else {
    		return new CommandResult(MESSAGE_FAILURE);
    	}
    }

}
