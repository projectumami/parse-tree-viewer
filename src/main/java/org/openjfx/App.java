package org.openjfx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application 
{
	private static Scene scene;
	private int sceneWidth;
	private int sceneHeight;
	private HashMap<Integer, LocationNode> locations = new HashMap<Integer, LocationNode>();

	@Override
	public void start(Stage primaryStage) 
	{
		ObjectMapper mapper = new ObjectMapper();

		sceneWidth = 1250;
		sceneHeight = 1250;
		List<TreeTableNode> treeTableNodes = null;

		try 
		{
			InputStream fileInputStream = new FileInputStream("C:\\ProjectUmami\\data\\tree.json");
			treeTableNodes = Arrays.asList(mapper.readValue(fileInputStream, TreeTableNode[].class));
			fileInputStream.close();

			for (TreeTableNode treeTableNode : treeTableNodes) 
			{
				System.out.println(treeTableNode.toString());
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		int previousLevel = -1;
		int numLevels = 0;
		int maxColumns = 0;

		int columns = 0;

		for (TreeTableNode node : treeTableNodes) 
		{
			numLevels = node.getLevel();

			if (previousLevel != numLevels) 
			{
				previousLevel = numLevels;

				if (columns > maxColumns) 
				{
					maxColumns = columns;
				}

				columns = 0;
			} 
			else 
			{
				if (node.getData().compareTo("<epsilon>") != 0)
				{
					columns++;
				}
			}
		}

		Pane root = new Pane();
		int border = 10;

		float rowInterval = sceneHeight / (numLevels + 1);
		float columnInterval = sceneWidth / (maxColumns + 1);

		previousLevel = -1;
		int currentLevel = 0;

		int column = 0;

		for (TreeTableNode node : treeTableNodes) 
		{
			currentLevel = node.getLevel();

			if (previousLevel != currentLevel) 
			{
				column = 0;

				previousLevel = currentLevel;
			}

			Group lineGroup = null;
			Group textGroup = null;
			Group textDataGroup = null;
			Rectangle r = null;
			
/*
			float width = ((columnInterval > rowInterval) ? rowInterval : columnInterval) * .75f;
			float height = ((columnInterval > rowInterval) ? rowInterval : columnInterval) * .75f;
			float x = border + column * columnInterval;
			float y = border + currentLevel * rowInterval;

			locations.put(node.getChildId(), new LocationNode(x, y));

			Rectangle r = new Rectangle();
			r.setX(x);
			r.setY(y);
			r.setWidth(width);
			r.setHeight(height);
			r.setArcWidth(20);
			r.setArcHeight(20);

			LocationNode parentLocation = locations.get(node.getParentId());

			Line line = new Line(x + width / 2.0f, y + height / 2.0f, parentLocation.getX() + width / 2.0f,
					parentLocation.getY() + height / 2.0f);

			Group lineGroup = new Group(line);

			Text text = new Text();
			text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
			text.setText(Integer.toString(node.getChildId()));
			text.setX(x + width * 0.1f);
			text.setY(y + height * 0.5f);
			text.setFill(Color.BEIGE);
			Group textGroup = new Group(text);
*/			


		
			if (node.getData().compareTo("<epsilon>") == 0) 
			{				
//				r.setStrokeWidth(5.0);
//				r.setStroke(Color.rgb(0, 255, 0, 0.90));
//				r.setFill(Color.rgb(0, 0, 0, 0.90));
			} 
			else 
			{
				float width = ((columnInterval > rowInterval) ? rowInterval : columnInterval) * .95f;
				float height = ((columnInterval > rowInterval) ? rowInterval : columnInterval) * .75f;
				float x = border + column * columnInterval;
				float y = border + currentLevel * rowInterval;

				locations.put(node.getChildId(), new LocationNode(x, y));

				r = new Rectangle();
				r.setX(x);
				r.setY(y);
				r.setWidth(width);
				r.setHeight(height);
				r.setArcWidth(10);
				r.setArcHeight(10);

				LocationNode parentLocation = locations.get(node.getParentId());

				Line line = new Line(x + width / 2.0f, y + height / 2.0f, parentLocation.getX() + width / 2.0f,
						parentLocation.getY() + height / 2.0f);

				lineGroup = new Group(line);

				Text text = new Text();
				text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
				text.setText(Integer.toString(node.getChildId()));
				text.setX(x + width * 0.05f);
				text.setY(y + height * 0.4f);
				text.setFill(Color.YELLOW);
				textGroup = new Group(text);
				
				Text textData = new Text();
				textData.setFont(Font.font("verdana", FontWeight.THIN, FontPosture.REGULAR, 10));
				textData.setText(node.getData());
				textData.setX(x + width * 0.05f);
				textData.setY(y + height * 0.7f);
				textData.setFill(Color.BEIGE);
				textDataGroup = new Group(textData);
				
				if (node.getNumChildren() == 0) 
				{
					r.setStrokeWidth(2.5);					
					r.setStroke(Color.rgb(0, 255, 0, 0.90));					
					r.setFill(Color.rgb(255, 0, 0, 0.90));
				} 
				else 
				{
					r.setFill(Color.rgb(0, 0, 200, 0.90));
				}
				
				column++;
			}

			if (r != null)
			{
				root.getChildren().add(r);
			}
			
			if (lineGroup != null)
			{
				root.getChildren().add(lineGroup);
			}

			if (textGroup != null)
			{
				root.getChildren().add(textGroup);
			}
			
			if (textDataGroup != null)
			{
				root.getChildren().add(textDataGroup);
			}

			if (lineGroup != null)
			{
				lineGroup.toBack();
			}


		}

		Scene scene = new Scene(root, sceneWidth, sceneHeight);

		primaryStage.setTitle("Treeviewer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	static void setRoot(String fxml) throws IOException 
	{
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException 
	{
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) 
	{
		launch();
	}

	private Path newPath() 
	{
		Path path = new Path(new MoveTo(0, 0), new HLineTo(100), new LineTo(70, 30), new ClosePath());
		path.setStroke(Color.DARKGRAY);
		path.setStrokeWidth(10);
		return path;
	}

}