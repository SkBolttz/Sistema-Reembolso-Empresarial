package br.com.hiquez.Controle_Rembolso_Corporativo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hiquez.Controle_Rembolso_Corporativo.Entity.Financeiro;

@Repository
public interface FinanceiroRepository extends JpaRepository<Financeiro, Long> {
    
}
