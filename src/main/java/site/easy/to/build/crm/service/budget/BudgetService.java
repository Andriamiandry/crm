package site.easy.to.build.crm.service.budget;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import site.easy.to.build.crm.entity.Budget;
import site.easy.to.build.crm.repository.BudgetRepository;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget save(Budget budget) {
        budgetRepository.save(budget);
        return budget;
    }

    public List<Budget> getCustomerBudgets(int customerId) {
        return budgetRepository.findByCustomerCustomerId(customerId);
    }
    public BigDecimal getTotalCustomerBudgets(int customerId) {
        List<Budget> budgets = getCustomerBudgets(customerId);
        BigDecimal totalBudget = BigDecimal.ZERO;
        for (Budget budget : budgets) {
            totalBudget = totalBudget.add(budget.getAmount());
        }
        return totalBudget;
    }

  
}
