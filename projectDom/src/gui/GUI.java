package gui;


import javafx.application.*;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import main.AppData;
import texteditor.NotePad;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent;

public class GUI extends Application {

	private AppData data;
	
	private VBox mainRootPane;
	private TabPane mainTabPane;
	private FlowPane mainCommandPane;
	
	private Scene scenery;
	
	private TextArea texts;
	private TextArea commands;

	
	public GUI() {
		data = AppData.getInstance();
	}
	
	@Override
	public void start(Stage arg) throws Exception {
		arg.setWidth(data.getWindowWidth());
		arg.setHeight(data.getWindowHeight());
		initComponents(arg);
	}
	
	/**
	 * Initializes the main components of the starting screen.
	 * @param arg
	 */
	public void initComponents(Stage arg) {
		mainRootPane = new VBox();
		
		mainTabPane = new TabPane();
		mainTabPane.setMinSize(data.getWindowWidth(), data.getWindowHeight() * 0.50);
		mainTabPane.setMaxSize(data.getWindowWidth(), data.getWindowHeight() * 0.50);
		mainTabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
		texts = initTextArea("design/contentStyle.css", data.getWindowWidth(), data.getWindowHeight() * 0.44);
		textHandler(texts);
		Tab t = new Tab();
		t.setContent(texts);
		mainTabPane.getTabs().add(t);
		
		
		
		commands = initTextArea("design/contentStyle.css", data.getWindowWidth(), data.getWindowHeight() * .40);
		
		
		mainRootPane.getChildren().add(mainTabPane);
		//mainRootPane.getChildren().add(1, new Label("THIS IS A TEST LABEL <<  THIS LABELWILL BE REMOVED AT A DIFFERENT DATE!"));
		mainRootPane.getChildren().add(commands);
		
		scenery = new Scene(mainRootPane);
		scenery.getStylesheets().add("design/contentStyle.css");
		
		arg.setScene(scenery);
		
		
		//arg.setResizable(false);
		arg.centerOnScreen();
		setStageHandlers(arg);
		arg.show();
	}
	/**
	 * Initializes the given text area along with setting the design of it.
	 * @param styleSheet the associated style sheet.
	 * @param width width of the component.
	 * @param height height of the component.
	 * @return the new textArea component.
	 */
	public TextArea initTextArea(String styleSheet, double width, double height) {
		TextArea ta = new TextArea();
		ta.setMinSize(width, height);
		ta.setMaxSize(width, height);
		
		return ta;
	}
	/**
	 * Sets the handlers/listeners for the stage upon resize.
	 * @param sta the stage to listen to.
	 */
	public void setStageHandlers(Stage sta) {
		sta.widthProperty().addListener((obs, oldWidth, newWidth) -> {
			data.setWindowWidth(newWidth.intValue());
			commands.setMinWidth(data.getWindowWidth());
			commands.setMaxWidth(data.getWindowWidth());
			mainTabPane.autosize();
		});
		
		sta.heightProperty().addListener((obs, oldHeight, newHeight) -> {
			data.setWindowHeight(newHeight.intValue());
			commands.setMinHeight(data.getWindowHeight() * 0.40);
			commands.setMaxHeight(data.getWindowHeight() * 0.40);
			mainTabPane.setMinHeight(data.getWindowHeight() * 60);
			mainTabPane.setMaxHeight(data.getWindowHeight() * 60);
			mainTabPane.autosize();
		});
	}
	public void textHandler(TextArea area) {
		area.setOnKeyPressed(new EventHandler<KeyEvent>()	{
			@Override 
			public void handle(KeyEvent k) {
				// get key info
				NotePad.getInstance().processInput(k.getCode().getName());
				
				// Action for prompt.
				if (NotePad.getInstance().isReady()) {
					NotePad.getInstance().saveNote(area.getText());
				}
				
				if (area.getText().length() > 5000) { area.clear(); }
				
				area.appendText(NotePad.getInstance().emptyDetailsToUser());
			}
		});
	}
}
