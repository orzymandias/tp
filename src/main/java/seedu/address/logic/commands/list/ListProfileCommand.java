package seedu.address.logic.commands.list;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_LIST_SUCCESS;
import static seedu.address.logic.commands.util.CommandUtil.getCommandResult;
import static seedu.address.model.FilterableItemList.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.address.model.util.ItemUtil.PROFILE_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.ui.tabs.TabName;

/**
 * Lists all profile items in the InternHunter to the user.
 */
public class ListProfileCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = String.format(MESSAGE_LIST_SUCCESS, PROFILE_NAME);
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProfileList(PREDICATE_SHOW_ALL_ITEMS);
        model.setProfileViewIndex(Index.fromOneBased(1)); // reset to zero for consistency
        return getCommandResult(model, MESSAGE_SUCCESS, TabName.PROFILE);
    }
}
