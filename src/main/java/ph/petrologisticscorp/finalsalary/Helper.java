package ph.petrologisticscorp.finalsalary;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Helper {
    public static void disableResize(ReadOnlyObjectProperty<Scene> sceneReadOnlyObjectProperty) {
        sceneReadOnlyObjectProperty.addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        ((Stage) newWindow).setResizable(false);
                    }
                });
            }
        });
    }

    public static List<Integer> generateYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int startInclusive = year - 80;
        return IntStream.range(startInclusive, year)
                .map(i -> year - i + startInclusive).boxed().collect(Collectors.toList());
    }

    public static Date toUtilDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }
}
