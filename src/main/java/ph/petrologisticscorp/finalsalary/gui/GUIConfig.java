package ph.petrologisticscorp.finalsalary.gui;

import com.github.vbauer.herald.ext.guice.LogModule;
import com.google.inject.AbstractModule;
import ph.petrologisticscorp.finalsalary.database.DatabaseModule;

public class GUIConfig extends AbstractModule {
    @Override
    protected void configure() {
        install(new LogModule());
        install(new DatabaseModule());
    }
}
