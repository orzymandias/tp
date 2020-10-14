package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EDIT_SUCCESS;
import static seedu.address.logic.commands.util.CommandUtil.getApplication;
import static seedu.address.logic.commands.util.CommandUtil.getCommandResult;
import static seedu.address.logic.parser.clisyntax.ApplicationCliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.clisyntax.ApplicationCliSyntax.PREFIX_STATUS_DATE;
import static seedu.address.model.FilterableItemList.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.address.model.util.ItemUtil.APPLICATION_ALIAS;
import static seedu.address.model.util.ItemUtil.APPLICATION_NAME;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FilterableItemList;
import seedu.address.model.Model;
import seedu.address.model.application.ApplicationItem;
import seedu.address.model.application.Status;
import seedu.address.model.application.StatusDate;
import seedu.address.model.internship.InternshipItem;
import seedu.address.ui.tabs.TabName;

public class EditApplicationCommand extends EditCommandAbstract {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + APPLICATION_ALIAS
            + ": Edits the details of a " + APPLICATION_NAME + " from InternHunter accessed "
            + "by the index number used in the displayed list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_STATUS_DATE + "STATUS_DATE]\n"
            + "Note: At least one of the optional fields must be provided. INDEX must be a positive integer.\n"
            + "Example: " + COMMAND_WORD + " " + APPLICATION_ALIAS + " 1 "
            + PREFIX_STATUS + "offered";

    private final Index targetIndex;
    private final EditApplicationDescriptor editApplicationDescriptor;

    /**
     * @param targetIndex of the application in the filtered application list to edit.
     * @param editApplicationDescriptor details to edit the application with.
     */
    public EditApplicationCommand(Index targetIndex, EditApplicationDescriptor editApplicationDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editApplicationDescriptor);

        this.targetIndex = targetIndex;
        this.editApplicationDescriptor = new EditApplicationDescriptor(editApplicationDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ApplicationItem applicationItemToEdit = getApplication(model, targetIndex);
        ApplicationItem editedApplicationItem = createEditedApplicationItem(applicationItemToEdit,
                editApplicationDescriptor);

        FilterableItemList<ApplicationItem> applicationList = model.getApplicationList();
        applicationList.setItem(applicationItemToEdit, editedApplicationItem);
        applicationList.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        String editSuccessMessage = String.format(MESSAGE_EDIT_SUCCESS, APPLICATION_NAME, editedApplicationItem);
        return getCommandResult(model, editSuccessMessage, TabName.APPLICATION);
    }

    /**
     * Creates and returns a {@code ApplicationItem} with the details of {@code applicationItemToEdit}
     * edited with {@code editApplicationDescriptor}.
     */
    private static ApplicationItem createEditedApplicationItem(ApplicationItem applicationItemToEdit,
            EditApplicationDescriptor editApplicationDescriptor) {

        assert applicationItemToEdit != null;

        // Keeps the same internship item
        InternshipItem internshipItem = applicationItemToEdit.getInternshipItem();
        Status updatedStatus = editApplicationDescriptor.getStatus().orElse(applicationItemToEdit.getStatus());
        StatusDate updatedStatusDate = editApplicationDescriptor
                .getStatusDate()
                .orElse(applicationItemToEdit.getStatusDate());

        return new ApplicationItem(internshipItem, updatedStatus, updatedStatusDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditApplicationCommand)) {
            return false;
        }

        // state check
        EditApplicationCommand e = (EditApplicationCommand) other;
        return targetIndex.equals(e.targetIndex)
                && editApplicationDescriptor.equals(e.editApplicationDescriptor);
    }

    /**
     * Stores the details to edit the application item with. Each non-empty field value will replace the
     * corresponding field value of the application.
     */
    public static class EditApplicationDescriptor {

        private Status status;
        private StatusDate statusDate;

        public EditApplicationDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditApplicationDescriptor(EditApplicationDescriptor toCopy) {
            setStatus(toCopy.status);
            setStatusDate(toCopy.statusDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(status, statusDate);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setStatusDate(StatusDate statusDate) {
            this.statusDate = statusDate;
        }

        public Optional<StatusDate> getStatusDate() {
            return Optional.ofNullable(statusDate);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditApplicationDescriptor)) {
                return false;
            }

            // state check
            EditApplicationDescriptor e = (EditApplicationDescriptor) other;

            return getStatus().equals(e.getStatus())
                    && getStatusDate().equals(e.getStatusDate());
        }
    }

}
