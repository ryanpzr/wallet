package com.ryanpzr.walletwizardservice.repositories;

import com.ryanpzr.walletwizardservice.model.income.Income;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface IncomeRepository extends JpaRepository<Income, Long> {
    // Interface que realiza as QUERY personalizadas no banco de dados
    @Modifying
    @Query("UPDATE Income SET total = (total + :valorCompra) WHERE mes = :nomeMes")
    void atualizarTotal(@Param("valorCompra") Double valorCompra, @Param("nomeMes") String nomeMes);

    @Query("SELECT SUM(receita) FROM Income")
    int findByReceita();

    @Query("SELECT i.mes FROM Income i WHERE mes = :mesReceita")
    String findByMes(String mesReceita);

}
