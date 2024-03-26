module org.isc415x.dan.creadormanual.cdmgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;

    opens org.isc415x.dan.creadormanual.cdmgui to javafx.fxml;
    exports org.isc415x.dan.creadormanual.cdmgui;
}