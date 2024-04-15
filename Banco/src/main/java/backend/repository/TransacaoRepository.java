package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.model.TransacaoModel;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoModel,Long>{

	public List<TransacaoModel> findAllByOrderByDataHoraDesc();
	public Optional<TransacaoModel> findById(Long id);

}
