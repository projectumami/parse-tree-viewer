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
	    		columns++;
	    	}
    	}
	    	
        Pane root = new Pane();
        int border = 10;
        
//        int numColumns = 50;
        float rowInterval = sceneHeight / (numLevels + 1);
        float columnInterval = sceneWidth / (maxColumns + 1);
        
//	    	LinkedHashMap<Integer, Integer> maxChildNodesByLevel = new LinkedHashMap<Integer, Integer>();
	    	
	    	previousLevel = -1;
	    	int currentLevel = 0;
	    	
//	    	LinkedHashMap<Integer, Integer> childNodesByParent = new LinkedHashMap<Integer, Integer>();
	    	
//	    	int row = 0;
	    	int column = 0;
	    	
	    	for (TreeTableNode node : treeTableNodes)
	    	{ 
//	    		if (node.getLevel() > 0) 
//	    		{
	    			currentLevel = node.getLevel();
	    			
//	    			System.out.println("Current Level = " + currentLevel);
	    			
		    		if (previousLevel != currentLevel)
		    		{
		    			column = 0;
//		    			System.out.println();
		    			
		    			previousLevel = currentLevel;
		    			
//		    			int maxNodes = 0;
		    			
		    			// Find the max child count in the current level.
//		    			for (Integer numNodes : childNodesByParent.values())
//		    			{
//		    				if (numNodes > maxNodes)
//		    				{
//		    					maxNodes = numNodes;
//		    				}
//		    			}
		    			
//		    			System.out.println(" Max Child Nodes of level " + currentLevel + "=" + maxNodes);
		    			
//		    			maxChildNodesByLevel.put(currentLevel, maxNodes);
		    			
//		    			childNodesByParent = new LinkedHashMap<Integer, Integer>();	    			
		    		}
	    						    		
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
	                
	                Line line = new Line(
	                		x + width / 2.0f, 
	                		y + height / 2.0f, 
	                		parentLocation.getX() + width / 2.0f, 
	                		parentLocation.getY() + height / 2.0f);
	                
	                Group lineGroup = new Group(line);

	                
	                Text text = new Text();
	                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
	                text.setText(Integer.toString(node.getChildId()));
	                text.setX(x);
	                text.setY(y + height * 0.75f);
	                text.setFill(Color.BEIGE);
	                
	                Group textGroup = new Group(text);	                
	                
	                if (node.getData().compareTo("<epsilon>") == 0)
	                {
	                	r.setFill(Color.rgb(0, 0, 0, 1.0));
	                }
	                else
	                {
	                	if (node.getNumChildren() == 0)
	                	{
	                		r.setFill(Color.rgb(255, 0, 0, 1.0));
	                	}
	                	else
	                	{
	                		r.setFill(Color.rgb(0, 0, 255, 1.0));	                		
	                	}
	                }

	                root.getChildren().add(r);	                
	                root.getChildren().add(lineGroup);

	                root.getChildren().add(textGroup);
	                //line.toBack();
	                lineGroup.toBack();
	                
//		    		Integer parent = childNodesByParent.get(node.getParentId());
		    		
//		    		System.out.println(" parent = " + parent + " node.getParentId() " + node.getParentId());
		    		
//		    		if (parent == null)
//		    		{
//		    			childNodesByParent.put(node.getParentId(), 1);
//		    		}
//		    		else
//		    		{
//		    			int numChildren = childNodesByParent.get(node.getParentId());
		    			
//		    			childNodesByParent.put(node.getParentId(), numChildren + 1);
//		    			System.out.println("Changing " + node.getParentId() + " from " + numChildren + " to " + (numChildren + 1));
//		    		}
//	    		}
		    		
	                column++;
	    	}
	    	
//	    	for (Integer key : maxChildNodesByLevel.keySet())
//	    	{
//	    		System.out.println("Key: " + key + " Value: " + maxChildNodesByLevel.get(key));	    		
//	    	}	    	

  	
    	
    	
    	

/*
        ArcTo arcTo = new ArcTo(); // ArcTo is set separately due to its complexity
        arcTo.setRadiusX(250);
        arcTo.setRadiusY(90);
        arcTo.setX(50);
        arcTo.setY(100);
        arcTo.setSweepFlag(true);
        System.out.println(arcTo.getXAxisRotation());

        Path path = new Path(
                new MoveTo(0, 0),
                new HLineTo(50),
                arcTo,
                new VLineTo(150),
                new HLineTo(0),
                new ClosePath()
        );

        SVGPath svgPath = new SVGPath();
        svgPath.setContent("M0,0 H50 A250,90 0 0,1 50,100 V150 H0 Z");
        // M - move, H - horizontal line, A - arc, V - vertical line, Z - close path
        svgPath.setFill(Color.DARKGREY);

        HBox root = new HBox(30, path, svgPath);
        root.setPadding(new Insets(20));

        primaryStage.setTitle("Paths");
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
 */

/*
        for (int column = 0; column < numColumns; column++)
        {
            for (int row = 0; row < numRows; row++)
            {
                Rectangle r = new Rectangle();
                r.setX(column * columnInterval);
                r.setY(row * rowInterval/);
                r.setWidth(((columnInterval > rowInterval) ? rowInterval : columnInterval) * .75f);
                r.setHeight(((columnInterval > rowInterval) ? rowInterval : columnInterval) * .75f);
                r.setArcWidth(20);
                r.setArcHeight(20);
                r.setFill(Color.rgb(0, 0, 255, 1.0));

                root.getChildren().addAll(r);
            }
        }
*/

        Scene scene = new Scene(root, sceneWidth, sceneHeight);

        primaryStage.setTitle("Treeviewer");
        primaryStage.setScene(scene);
        primaryStage.show();


/*
        Rectangle rect1 = new Rectangle(150, 50);
        rect1.setArcHeight(10);
        rect1.setArcWidth(10);
        rect1.setFill(Color.BLUE);

        Rectangle rect2 = new Rectangle(150, 50);
        rect2.setArcHeight(10);
        rect2.setArcWidth(10);
        rect2.setFill(Color.RED);


        Circle circle = new Circle(50);
        circle.setFill(Color.DARKGREY);

        Ellipse ellipse = new Ellipse();
        ellipse.setRadiusX(60);
        ellipse.setRadiusY(40);
        ellipse.setFill(Color.DARKGREY);

        Polygon polygon = new Polygon();
        polygon.setFill(Color.DARKGREY);
        polygon.getPoints().addAll(
                0.0, 0.0,
                50.0, 30.0,
                10.0, 60.0);



        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(20));
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(rect1, rect2);

        primaryStage.setTitle("Closed Shapes");
        primaryStage.setScene(new Scene(hbox, 500, 500));
        primaryStage.show();

 */

/*
        Text txt = new Text("Hello\nJavaFX!");
        txt.setFont(Font.font("Courier New", FontWeight.BOLD, FontPosture.ITALIC, 30));

        Stop[] stops = new Stop[]{new Stop(0, Color.BLACK), new Stop(1, Color.DARKGRAY), new Stop(0.5, Color.ANTIQUEWHITE)};
        LinearGradient gradient = new LinearGradient(50, 50, 250, 50, false, CycleMethod.NO_CYCLE, stops);
        txt.setFill(gradient);

        Text txt1 = new Text("Text1");
        txt1.setFont(Font.font("Courier New", 15));
        Text txt2 = new Text("Text2");
        txt2.setFont(Font.font("Times New Roman", 20));
        Text txt3 = new Text("Text3");
        txt3.setFont(Font.font("Arial", 30));
        TextFlow textFlow = new TextFlow(txt1, txt2, txt3);

        VBox root = new VBox(30, txt, textFlow);
        root.setPadding(new Insets(20));
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
 */




/*
        FlowPane root = new FlowPane(20, 40);
        root.setPadding(new Insets(20));
        for (StrokeLineJoin value : StrokeLineJoin.values()) {
            Path path = newPath();
            path.setStrokeLineJoin(value);
            root.getChildren().add(new VBox(10, new Text(value.name()), path));
        }

        primaryStage.setTitle("Dashes");
        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();
 */

/*
        Rectangle shape = new Rectangle(400, 100);
        shape.setFill(Color.WHITE);

        shape.setStroke(Color.RED);
        shape.setStrokeWidth(10);
        shape.setStrokeType(StrokeType.CENTERED);

        shape.setStrokeDashOffset(20);
        shape.getStrokeDashArray().addAll(100.0, 50.0);
        shape.setStrokeLineCap(StrokeLineCap.BUTT);
        shape.setStrokeLineJoin(StrokeLineJoin.MITER);
        shape.setStrokeMiterLimit(0);

        StackPane root = new StackPane();
        root.getChildren().add(shape);

        Scene scene = new Scene(root, 600, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
*/
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    private Path newPath() {
        Path path = new Path(
                new MoveTo(0, 0), new HLineTo(100),
                new LineTo(70, 30), new ClosePath());
        path.setStroke(Color.DARKGRAY);
        path.setStrokeWidth(10);
        return path;
    }

}