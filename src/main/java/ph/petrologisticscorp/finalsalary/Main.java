package ph.petrologisticscorp.finalsalary;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.h2.tools.Server;
import ph.petrologisticscorp.finalsalary.gui.GUI;
import ph.petrologisticscorp.finalsalary.gui.GUIConfig;

import java.sql.DriverManager;


public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                install(new GUIConfig());
            }
        });

        try {
            Server.createTcpServer("-tcpAllowOthers").start();
            Class.forName("org.h2.Driver");
            DriverManager.getConnection("jdbc:log4jdbc:h2:file:./data/plc;AUTO_SERVER=TRUE;TRACE_LEVEL_FILE=4");
            final Server webServer = Server.createWebServer();
            webServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GUI gui = injector.getInstance(GUI.class);

        try {
            gui.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
