package seedu.addressbook.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;


/**
 * Finds and deletes all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindAndDeleteCommand extends Command {

    public static final String COMMAND_WORD = "findAndDelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and deletes them.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private Set<String> keywords;
    
    public FindAndDeleteCommand(Set<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public CommandResult execute() {
        try {
            List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);
            
            for (ReadOnlyPerson target : personsFound) {
                addressBook.removePerson(target);
            }
            if (personsFound.isEmpty()) {
                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }
            return new CommandResult(getMessageForPersonDeletedListShownSummary(personsFound), personsFound);
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }
    
    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }
}
