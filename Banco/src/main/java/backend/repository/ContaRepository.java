package backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.model.ContaModel;

@Repository
public interface ContaRepository extends JpaRepository<ContaModel,Long>{
	
	public Optional<ContaModel> findById(Long id);

}
