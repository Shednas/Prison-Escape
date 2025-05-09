module com.game.PrisonEscape {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.game.PrisonEscape to javafx.fxml;
    exports com.game.PrisonEscape;
    exports com.game.PrisonEscape.menu;
    opens com.game.PrisonEscape.menu to javafx.fxml;
    exports com.game.PrisonEscape.character;
    opens com.game.PrisonEscape.character to javafx.fxml;
    exports com.game.PrisonEscape.gameLogic;
    opens com.game.PrisonEscape.gameLogic to javafx.fxml;
}