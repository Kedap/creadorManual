module org.isc4x51.dan.creadormanual.cdmgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;

    opens org.isc4x51.dan.creadormanual.cdmgui to javafx.fxml;
    exports org.isc4x51.dan.creadormanual.cdmgui;
}