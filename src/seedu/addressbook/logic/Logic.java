package seedu.addressbook.logic;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.ViewAllCommand;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.parser.Parser;
import seedu.addressbook.storage.StorageFile;
import seedu.addressbook.password.PasswordFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents the main Logic of the AddressBook.
 */
public class Logic {
	
	private String userpass = "";


    private StorageFile storage;
    private AddressBook addressBook;
    private String myPassword;
    private PasswordFile password;

    /** The list of person shown to the user most recently.  */
    private List<? extends ReadOnlyPerson> lastShownList = Collections.emptyList();

    public Logic() throws Exception{
        setStorage(initializeStorage());
        setAddressBook(storage.load());
        password = new PasswordFile();
        myPassword = password.load();
    }

    
    Logic(StorageFile storageFile, AddressBook addressBook) throws Exception{
        setStorage(storageFile);
        setAddressBook(addressBook);
        password = new PasswordFile();
        myPassword = password.load();
    }

    public boolean ChangePassword(String oldPassword, String newPassword) {
        if (myPassword.compareTo(oldPassword) == 0) {
            password.save(newPassword);
            myPassword = newPassword;
        } else {
        	return false;
        }
        return true;
    }
    
    void setStorage(StorageFile storage){
        this.storage = storage;
    }

    void setAddressBook(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    /**
     * Creates the StorageFile object based on the user specified path (if any) or the default storage path.
     * @throws StorageFile.InvalidStorageFilePathException if the target file path is incorrect.
     */
    private StorageFile initializeStorage() throws StorageFile.InvalidStorageFilePathException {
        return new StorageFile();
    }

    public String getStorageFilePath() {
        return storage.getPath();
    }

    /**
     * Unmodifiable view of the current last shown list.
     */
    public List<ReadOnlyPerson> getLastShownList() {
        return Collections.unmodifiableList(lastShownList);
    }

    protected void setLastShownList(List<? extends ReadOnlyPerson> newList) {
        lastShownList = newList;
    }

    /**
     * Parses the user command, executes it, and returns the result.
     * @throws Exception if there was any problem during command execution.
     */
    public CommandResult execute(String userCommandText) throws Exception {
    	
    	String usertext = userCommandText;
    	
    	Command command;
    	if (ViewAllCommand.flag){
    		if (userCommandText.equals(password.load())) {
    			command = new ViewAllCommand(1);
    	    	System.out.println("viewall created");

    		} else {
    			ViewAllCommand.flag = false;
    			command = new ViewAllCommand(ViewAllCommand.index);
    		}
    		
    	} else {
            command = new Parser().parseCommand(userCommandText, this);

    	}
    	
        CommandResult result = execute(command);
        recordResult(result);
    	System.out.println("after execute");

        return result;
    }

    /**
     * Executes the command, updates storage, and returns the result.
     *
     * @param command user command
     * @return result of the command
     * @throws Exception if there was any problem during command execution.
     */
    private CommandResult execute(Command command) throws Exception {
        command.setData(addressBook, lastShownList);
        CommandResult result = command.execute();
        storage.save(addressBook);
        return result;  
        
    }

    /** Updates the {@link #lastShownList} if the result contains a list of Persons. */
    private void recordResult(CommandResult result) {
        final Optional<List<? extends ReadOnlyPerson>> personList = result.getRelevantPersons();
        if (personList.isPresent()) {
            lastShownList = personList.get();
        }
    }
}
