package ph.petrologisticscorp.finalsalary.gui;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.Module;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

public class GUI extends Application {

    private GuiceContext guiceContext;

    @Override
    public void start(Stage primaryStage) throws Exception {
        guiceContext = new GuiceContext(this, new Supplier<Collection<Module>>() {
            @Override
            public Collection<Module> get() {
                return Arrays.asList(new GUIConfig());
            }
        });
        guiceContext.init();
        final WindowManager stageController = guiceContext.getInstance(WindowManager.class);
        stageController.switchScene(WindowManager.SCENES.PERSON_LIST_SCENE);

        Parent r = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        primaryStage.setScene(new Scene(r, 200, 200));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("JUREL");
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}
