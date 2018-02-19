package gui;


import java.io.File;

import javafx.application.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

public class Window extends Application {

	private int width;
	private int height;
	private GraphicsContext gc;
	private GridPane pane;
	private Scene scene;
	
	public Window() {
		width = 1280;
		height = 720;
	}
	
	@Override
	public void start(Stage arg) throws Exception {
		arg.setWidth(width);
		arg.setHeight(height);
		initComponents(arg);
	}
	public void initComponents(Stage arg) {
		pane = new GridPane();
		scene = new Scene(pane);
		Canvas canv = new Canvas(width, height);
		
		Button b = new Button();
		b.setMinWidth(width/5);
		b.setMinHeight(height/3 - 12);
		
		Button a = new Button();
		a.setMinWidth(width/5);
		a.setMinHeight(height/3 - 12);
		
		Button e = new Button();
		e.setMinWidth(width/5);
		e.setMinHeight(height/3 - 12);
		
		pane.add(b, 0, 0);
		pane.add(a, 0, 1);
		pane.add(e, 0, 2);
		arg.setScene(scene);
		arg.centerOnScreen();
		arg.show();
	}
}
