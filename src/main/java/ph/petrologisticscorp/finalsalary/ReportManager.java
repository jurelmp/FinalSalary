package ph.petrologisticscorp.finalsalary;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@Singleton
public class ReportManager {

    public enum REPORTS {
        SALARIES("/reports/Salaries.jrxml"),
        LEAVES("/reports/Leaves.jrxml"),
        INDIVIDUAL("/reports/Individual.jrxml");

        private String reportName;

        REPORTS(String templatePath) {
            this.reportName = templatePath;
        }

        public String getReportTemplate() {
            return reportName;
        }
    }

    public void generateReport(REPORTS report, Map parameters)
            throws ClassNotFoundException, SQLException, JRException {
        Class.forName("org.h2.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:log4jdbc:h2:file:./data/plc;AUTO_SERVER=TRUE;TRACE_LEVEL_FILE=4");

        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream(report.getReportTemplate()));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
        JasperViewer.viewReport(jasperPrint, false);
    }
}
