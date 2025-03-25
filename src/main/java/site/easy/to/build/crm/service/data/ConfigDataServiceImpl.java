package site.easy.to.build.crm.service.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ConfigDataServiceImpl implements ConfigDataService {

    @PersistenceContext // Injecte l'EntityManager
    private EntityManager entityManager;

    @Override
    @Transactional // Gère la transaction
    public String resetData() {
        try {
            // Désactiver les vérifications des clés étrangères
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

            // Liste des tables à tronquer
            String[] tables = {
                    "employee",
                    "email_template",
                    "customer_login_info",
                    "customer",
                    "trigger_lead",
                    "trigger_ticket",
                    "trigger_ticket_histo",
                    "ticket_expense",
                    "trigger_contract",
                    "lead_action",
                    "google_drive_file",
                    "file",
                    "contract_settings",
                    "lead_settings",
                    "ticket_settings",
                    "customer"
            };

            // Tronquer chaque table
            for (String table : tables) {
                entityManager.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
            }

            // Réactiver les vérifications des clés étrangères
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

            return "Data reset successfully.";
        } catch (Exception e) {
            // En cas d'erreur, la transaction sera annulée automatiquement
            e.printStackTrace();
            return "Failed to reset: " + e.getMessage();
        }
    }
}