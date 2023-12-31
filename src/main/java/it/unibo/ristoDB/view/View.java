package it.unibo.ristoDB.view;

import java.sql.Connection;

import it.unibo.ristoDB.model.Features;

public interface View {

    /** Set the Login scene. */
    void setLoginScene();

    /** Set the back office scene on stage and connect it to its controller. */
    void setBackOfficeScene();

    /** Opens a new pop-up stage for the document manager scene and links it to its controller. */
    void setFrontOfficeScene();

    /** Set order scene on stage and connect it to its controller. */
    void setOrderScene(int selectedTable);

    /** Set manageWorkshift scene on stage and connect it to its controller. */
    void setManageWorkshiftScene();

    /**
     * Add a connection to a DB.
     * @param connection The connection to the database
     */
    void addConnection(Connection connection);

    Features getFeatures();

}