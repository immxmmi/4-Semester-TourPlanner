module at.technikum.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires lombok;
    requires org.apache.logging.log4j;
    requires org.slf4j;
    requires reload4j;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.apache.pdfbox;


    opens at.technikum.tourplanner to javafx.fxml;
    exports at.technikum.tourplanner;
    exports at.technikum.tourplanner.views;
    opens at.technikum.tourplanner.views to javafx.fxml;
    exports at.technikum.tourplanner.view.viewmodel;
    opens at.technikum.tourplanner.view.viewmodel to javafx.fxml;
    exports at.technikum.tourplanner.view.controller;
    opens at.technikum.tourplanner.view.controller to javafx.fxml;
    exports at.technikum.tourplanner.business;
    opens at.technikum.tourplanner.business to javafx.fxml;
}