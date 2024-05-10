package com.ryanpzr.walletwizardservice.repositories;

import com.ryanpzr.walletwizardservice.model.expense.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;


public interface ExpensesRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e JOIN FETCH e.income i WHERE i.mes = :dateParam")
    Page<Expense> listarPageWithYear(@Param("dateParam") String dateParam, Pageable pageable);
    @Query("SELECT e FROM Expense e WHERE e.nomeCompra = :nomeDaCompra")
    Optional<Expense> findByNomeCompra(@Param("nomeDaCompra") String nomeDaCompra);
    @Transactional
    @Modifying
    @Query("DELETE FROM Expense e WHERE e.nomeCompra = :nomeDaCompra")
    void deletarGasto(String nomeDaCompra);
    @Transactional
    @Modifying
    @Query("UPDATE Income i SET i.total = (i.total - (SELECT e.valorCompra FROM Expense e WHERE e.nomeCompra = :nomeDaCompra)) WHERE i.mes = :mesParam")
    void atualizarTotalIncome(@Param("nomeDaCompra") String nomeDaCompra, @Param("mesParam") String mesParam);
}
