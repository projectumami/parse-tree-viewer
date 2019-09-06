package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.VLineTo;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.io.IOException;

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
        sceneWidth = 1000;
        sceneHeight = 1000;
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

        float rowInterval = sceneHeight / 25;
        float columnInterval = sceneWidth / 25;

        for (int column = 0; column < 25; column++)
        {
            for (int row = 0; row < 25; row++)
            {
                Rectangle r = new Rectangle();
                r.setX(column * columnInterval - columnInterval / 2);
                r.setY(row * rowInterval - rowInterval / 2);
                r.setWidth(columnInterval / 2);
                r.setHeight(rowInterval / 2);
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