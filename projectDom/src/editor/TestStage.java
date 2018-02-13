package editor;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import main.AppData;
import main.Command;

public class TestStage extends Stage {
	
	private FlowPane fp;
	private Scene sc;
	private Stage ancestor;
	private TextArea ta;
	private AppData data;
	private StringBuilder currentToken;
	
	public TestStage(Stage ancestor, AppData data) {
		this.ancestor = ancestor;
		this.data = data;
		setWidth(data.getWindowWidth());
		setHeight(data.getWindowHeight());
		setResizable(false);
		initComponents();
	}
	/**
	 * Initializes the components of pane.
	 */
	public void initComponents() {
		fp = new FlowPane();
		sc = new Scene(fp);
		
		currentToken = new StringBuilder();
		
		fp.getChildren().add(initMainComponent());
		centerOnScreen();
		setScene(sc);
	}
	/**
	 * Initializes the main component of the editor stage.
	 * @return the functioning text area.
	 */
	public TextArea initMainComponent() {
		TextArea ta = new TextArea();
		ta.setMinSize(data.getWindowWidth(), data.getWindowHeight());
		ta.setMaxSize(data.getWindowWidth(), data.getWindowHeight());
		ta.getStylesheets().add("design/contentStyle.css");
		
		ta.setOnKeyPressed(new EventHandler<KeyEvent>()	{
			@Override public void handle(KeyEvent k) {
				
				if (k.getCode().isWhitespaceKey()) {
					if (Command.isCommand(currentToken.toString())) {
						// TODO exectue the given command
						System.out.println(currentToken.toString() + " is a command.");
					}
					currentToken.delete(0, currentToken.length());
				} else {
					if (k.getCode().getName().equals("Minus")) {
						currentToken.append(k.getText());
					} else if (k.getCode().isLetterKey()) {
						currentToken.append(k.getText());
					} else if (k.getCode().getName().equals("Backspace")) {
						if (currentToken.length() > 0) {
							currentToken.deleteCharAt(currentToken.length() - 1);
						}
					}
				}
				
				System.out.println("current token: " + currentToken.toString());
			}
		});
		return ta;
	}
}
