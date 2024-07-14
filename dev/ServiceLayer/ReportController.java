package ServiceLayer;

import DomainLayer.ReportGeneration;

public class ReportController {


    /**
     * Name: showExpReportsCtr - Use Inventory's method to return the expired items report.
     * Args: None
     * Returns: String - The report (Presentation prints).
     * */
    public static String showExpReportsCtr(){
        return ReportGeneration.generateReportExpired();
    }

    /**
     * Name: showDamageReportsCtr - Use Inventory's method to return the damaged items report.
     * Args: None
     * Returns: String - The report (Presentation prints).
     * */
    public static String showDamageReportsCtr(){
        return ReportGeneration.generateReportDamage();
    }

}
