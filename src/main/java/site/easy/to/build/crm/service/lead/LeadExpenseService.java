package site.easy.to.build.crm.service.lead;
import site.easy.to.build.crm.entity.LeadExpense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface LeadExpenseService {
    BigDecimal getTotalExpensesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    public LeadExpense save(LeadExpense leadExpense);
    
    LeadExpense findById(int id);
    public LeadExpense findLatestByTriggerLeadHistoId(Integer triggerLeadHistoId);  
}
