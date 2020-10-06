package seedu.address.model.internship;

import seedu.address.model.wrapper.AlphaNumericWord;

public class JobTitle extends AlphaNumericWord {

    public static final String MESSAGE_CONSTRAINTS =
            "Job titles should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * Constructs a {@code JobTitle}.
     *
     * @param jobTitle A valid job title.
     */
    public JobTitle(String jobTitle) {
        super(jobTitle, MESSAGE_CONSTRAINTS);
    }

}
