module treeviewer {
    requires javafx.controls;
    requires javafx.fxml;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.core;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}