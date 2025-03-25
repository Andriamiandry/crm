package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.TicketHisto;
import site.easy.to.build.crm.entity.TriggerLeadHisto;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketHistoRepository extends JpaRepository<TicketHisto, Integer> {
    public TicketHisto findById(int ticketId);

    @Query("SELECT t FROM TicketHisto t " +
            "WHERE (:date1 IS NULL AND :date2 IS NULL AND t.deleteAt IS NULL) " +  // Cas où les deux dates sont null
            "OR (:date1 IS NOT NULL OR :date2 IS NOT NULL) " +                     // Cas où au moins une date est non-null
            "AND (t.createdAt <= COALESCE(:date2, CURRENT_TIMESTAMP)) " +
            "AND (t.deleteAt IS NULL OR t.deleteAt >= COALESCE(:date1, t.createdAt))")
    List<TicketHisto> getBetweenDate(
            @Param("date1") LocalDateTime date1,
            @Param("date2") LocalDateTime date2);

    List<TicketHisto> findByDeleteAtIsNull();
    public List<TicketHisto> findByCustomerCustomerId(int customerId);
}
