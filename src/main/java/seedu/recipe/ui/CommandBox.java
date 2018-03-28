package seedu.recipe.ui;

import static seedu.recipe.ui.util.KeyboardShortcutsMapping.COMMAND_SUBMISSION;
import static seedu.recipe.ui.util.KeyboardShortcutsMapping.LAST_COMMAND;
import static seedu.recipe.ui.util.KeyboardShortcutsMapping.NEW_LINE_IN_COMMAND;
import static seedu.recipe.ui.util.KeyboardShortcutsMapping.NEXT_COMMAND;
import static seedu.recipe.ui.util.KeyboardShortcutsMapping.SHOW_SUGGESTIONS_COMMAND;

import java.util.logging.Logger;

import com.dropbox.core.DbxException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.events.ui.NewResultAvailableEvent;
import seedu.recipe.logic.ListElementPointer;
import seedu.recipe.logic.Logic;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final char LF = '\n';

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    @FXML
    private TextArea commandTextArea;
    private SuggestionsPopUp suggestionsPopUp;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextArea.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        suggestionsPopUp = new SuggestionsPopUp(this);
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        suggestionsPopUp.hideSuggestions();
        if (COMMAND_SUBMISSION.match(keyEvent)) {
            keyEvent.consume();
            submitCommand();
        } else if (LAST_COMMAND.match(keyEvent)) {
            keyEvent.consume();
            navigateToPreviousInput();
        } else if (NEXT_COMMAND.match(keyEvent)) {
            keyEvent.consume();
            navigateToNextInput();
        } else if (NEW_LINE_IN_COMMAND.match(keyEvent)) {
            keyEvent.consume();
            createNewLine();
        } else if (SHOW_SUGGESTIONS_COMMAND.match(keyEvent)) {
            keyEvent.consume();
            suggestionsPopUp.showSuggestions();
        }
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    protected void replaceText(String text) {
        commandTextArea.setText(text);
        commandTextArea.positionCaret(commandTextArea.getText().length());
    }

    /**
     * Append a line feed character to the command area
     */
    private void createNewLine() {
        int caretPosition = commandTextArea.getCaretPosition();
        StringBuilder commandTextStringBuilder = new StringBuilder(commandTextArea.getText());
        commandTextStringBuilder.insert(caretPosition, LF);
        String newCommandText = commandTextStringBuilder.toString();
        commandTextArea.setText(newCommandText);
        commandTextArea.positionCaret(caretPosition + 1);
    }

    /**
     * Handles the command submission.
     */
    @FXML
    private void submitCommand() {
        try {
            CommandResult commandResult = logic.execute(commandTextArea.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextArea.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException | DbxException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextArea.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextArea.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextArea.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Gets TextArea object in CommandBox
     */
    protected TextArea getCommandTextArea() {
        return commandTextArea;
    }
}