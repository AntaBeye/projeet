module sn.dev.systemparrainage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires TrayNotification;
    requires fontawesomefx;
//    requires mysql.connector.java;


    opens sn.dev.systemparrainageapp to javafx.fxml;
    exports sn.dev.systemparrainageapp;
    exports sn.dev.systemparrainageapp.controllers;
    opens sn.dev.systemparrainageapp.controllers to javafx.fxml;
    exports sn.dev.systemparrainageapp.entities;
    opens sn.dev.systemparrainageapp.entities to javafx.fxml;
}