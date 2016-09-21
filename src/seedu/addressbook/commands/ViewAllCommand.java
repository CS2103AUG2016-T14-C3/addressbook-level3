package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;


/**
 * Shows all details of the person identified using the last displayed index.
 * Private contact details are shown.
 */
public class ViewAllCommand extends Command {
	
	public static boolean flag = false;
	
	public static int index;
	
    public static final String COMMAND_WORD = "viewall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows all details of the person "
            + "identified by the index number in the last shown person listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1" + "\n *FEATURE REQUIRES PASSWORD TO BE ENTERED*" ;

    public static final String MESSAGE_VIEW_PERSON_DETAILS = "Viewing person: %1$s";


    public ViewAllCommand(int targetVisibleIndex) {
    	
        super(targetVisibleIndex);
        index = targetVisibleIndex;
    }


    @Override
    public CommandResult execute() {
    	
    	if (flag == true) {
    	
	        try {
	            final ReadOnlyPerson target = getTargetPerson();
	            if (!addressBook.containsPerson(target)) {
	                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
	            }
    			ViewAllCommand.flag = false;
	            return new CommandResult(String.format(MESSAGE_VIEW_PERSON_DETAILS, target.getAsTextShowAll()));
	        } catch (IndexOutOfBoundsException ie) {
	            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
	        }
	    }
    	
    	else {
			ViewAllCommand.flag = true;

    		return new CommandResult("Key in Password");
    	}
    	
    }
    
    
}
