package gui;



import java.awt.Dimension;
import java.awt.Toolkit;

import editor.NotePad;
import editor.TestStage;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import main.AppData;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Main extends Application {

	private AppData data;
	
	private FlowPane pane;
	private Scene scenery;
	private TextArea area;
	
	public Main() {
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
		pane = new FlowPane();
		scenery = new Scene(pane);
		
		area = initMainProcessorSetup();
		initMainProcessorHandler(area);
		
		pane.getChildren().add(area);
		
		arg.setScene(scenery);
		arg.setResizable(false);
		arg.centerOnScreen();
		arg.show();
	}
	
	public TextArea initMainProcessorSetup() {
		TextArea ta = new TextArea();
		ta.setMinSize(data.getWindowWidth(), data.getWindowHeight() - 25);
		ta.setMaxSize(data.getWindowWidth(), data.getWindowHeight() - 25);
		ta.getStylesheets().add("design/contentStyle.css");
		
		return ta;
	}
	
	public void initMainProcessorHandler(TextArea area) {
		area.setOnKeyPressed(new EventHandler<KeyEvent>()	{
			@Override 
			public void handle(KeyEvent k) {
				// get key info
				NotePad.getInstance().processInput(k.getCode().getName());
				
				// Action for prompt.
				if (NotePad.getInstance().isReady()) {
					NotePad.getInstance().saveNote(area.getText());
				}
				
				// message to user.
				String r = NotePad.getInstance().emptyDetailsToUser();
				if (!r.isEmpty()) {
					area.appendText(r);
				}
				
			}
		});
	}
}
