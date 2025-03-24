package site.easy.to.build.crm.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class DataRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String resetDatabase() {
        try {
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE employee;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE email_template;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE customer_login_info;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE customer;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE trigger_lead;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE trigger_ticket;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE trigger_contract;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE contract_settings;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE lead_action;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE lead_settings;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE ticket_settings;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE file;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE google_drive_file;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE trigger_lead_histo;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE lead_expense;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE trigger_ticket_histo;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE ticket_expense;").executeUpdate();
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate(); 

            
        } catch (Exception e) {
            return "Failed to Reset : "+e.getMessage();
        }
        return "Data reseted Successfully";
    }

    @Transactional
public String importCSV(String csvFile) {
    try {
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;
        String[] headers = null;

        String regex = ";";

        if ((line = br.readLine()) != null) {
            headers = line.split(regex); // Supposons que le CSV est séparé par des virgules
        }

        if (headers == null || headers.length == 0) {
            throw new IllegalArgumentException("Le fichier CSV est vide ou mal formaté.");
        }

        String tempTableName = generateTempTableName();

        createTempTable(tempTableName, headers);

        while ((line = br.readLine()) != null) {
            String[] values = line.split(regex);
            insertDataIntoTable(tempTableName, headers, values);
        }

        saveTempTableName(tempTableName);

        // ---------------------------------------------------------
            
        // ---------------------------------------------------------

        br.close();
    } catch (IOException e) {
        return "Failed to read CSV file : " + e.getMessage();
    }
    return "Success";
}

private String generateTempTableName() {
    String sql = "SELECT MAX(id) FROM temp_tab";
    Integer lastId = (Integer) entityManager.createNativeQuery(sql).getSingleResult();
    int newId = (lastId != null) ? lastId + 1 : 1;
    return "table_temp_" + newId;
}

private void createTempTable(String tableName, String[] headers) {
    StringBuilder sql = new StringBuilder("CREATE TABLE `" + tableName + "` (");
    sql.append("id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, ");

    for (String header : headers) {
        sql.append("`").append(header).append("` VARCHAR(255) ,");
    }

    sql.delete(sql.length() - 1, sql.length()); // Supprimer la dernière virgule
    sql.append(");");

    
    // System.out.println("---------------");
    // System.out.println(sql.toString());
    // System.out.println("---------------");
    
    entityManager.createNativeQuery(sql.toString()).executeUpdate();

}

private void insertDataIntoTable(String tableName, String[] headers, String[] values) {
    StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
    for (String header : headers) {
        sql.append("`").append(header).append("`").append(", ");
    }
    sql.delete(sql.length() - 2, sql.length()); // Supprimer la dernière virgule
    sql.append(") VALUES (");

    for (String value : values) {
        sql.append("'").append(value).append("', ");
    }
    sql.delete(sql.length() - 2, sql.length()); // Supprimer la dernière virgule
    sql.append(");");

    entityManager.createNativeQuery(sql.toString()).executeUpdate();
}

private void saveTempTableName(String tableName) {
    String sql = "INSERT INTO temp_tab (name) VALUES (:tableName)";
    entityManager.createNativeQuery(sql)
                 .setParameter("tableName", tableName)
                 .executeUpdate();
}
}