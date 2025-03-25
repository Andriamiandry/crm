package site.easy.to.build.crm.service.budget;

import java.math.BigDecimal;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
=======
import java.util.List;
>>>>>>> Stashed changes
=======
import java.util.List;
>>>>>>> Stashed changes
=======
import java.util.List;
>>>>>>> Stashed changes

import org.springframework.stereotype.Service;

import site.easy.to.build.crm.entity.Budget;
import site.easy.to.build.crm.repository.BudgetRepository;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
=======
    public BudgetService() {
        this.budgetRepository = null;
>>>>>>> Stashed changes
=======
    public BudgetService() {
        this.budgetRepository = null;
>>>>>>> Stashed changes
=======
    public BudgetService() {
        this.budgetRepository = null;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
  
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    // public BigDecimal getRealBudget(int customerId) {
    //     return getTotalCustomerBudgets(customerId).subtract(expenseService.getCustomerDepense(customerId));
    // }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
