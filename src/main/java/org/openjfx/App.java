package org.openjfx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private int sceneWidth;
    private int sceneHeight;

    @Override
    public void start(Stage primaryStage)
    {    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	try
    	{
	    	InputStream fileInputStream = new FileInputStream("C:\\ProjectUmami\\data\\tree.json");
	    	List<TreeTableNode> treeTableNodes = Arrays.asList(mapper.readValue(fileInputStream, TreeTableNode[].class));
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
    	
        sceneWidth = 1000;
        sceneHeight = 500;
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
        Pane root = new Pane();

        int numRows = 7;
        int numColumns = 25;
        float rowInterval = sceneHeight / numRows;
        float columnInterval = sceneWidth / numColumns;

        for (int column = 0; column < numColumns; column++)
        {
            for (int row = 0; row < numRows; row++)
            {
                Rectangle r = new Rectangle();
                r.setX(column * columnInterval /* + columnInterval / 2 */);
                r.setY(row * rowInterval /* - rowInterval / 2 */);
                r.setWidth(((columnInterval > rowInterval) ? rowInterval : columnInterval) * .75f);
                r.setHeight(((columnInterval > rowInterval) ? rowInterval : columnInterval) * .75f);
                r.setArcWidth(20);
                r.setArcHeight(20);
                r.setFill(Color.rgb(0, 0, 255, 1.0));

                root.getChildren().addAll(r);
            }
        }

        Scene scene = new Scene(root, sceneWidth, sceneHeight);

        primaryStage.setTitle("Color Demo");
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