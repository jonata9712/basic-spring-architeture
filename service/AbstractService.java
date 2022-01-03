
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.bernhoeft.meetings.converter.Converter;
import br.com.bernhoeft.meetings.domain.AbstractEntity;
import br.com.bernhoeft.meetings.dto.AbstractDTO;
import br.com.bernhoeft.meetings.exception.ResourceNotFoundException;

public abstract class AbstractService<T extends AbstractEntity, DTO extends AbstractDTO, R extends JpaRepository<T, Long>, C extends Converter<T, DTO>> {
	
	protected R repository;
	protected C converter;

	public AbstractService(R repository, C converter) {
		this.repository = repository;
		this.converter = converter;
	}
	
	public DTO save(DTO dto) {
		return converter.from(repository.save(converter.to(dto)));
	}
	
	public DTO get(Long id) {
		return converter.from(repository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Não encontrado")));
	}
	
	public List<DTO> getAll(){
		List<DTO> response = new ArrayList<>();
		
		repository.findAll().forEach(i -> {
			response.add(converter.from(i));
		});
		return response;
	}
	
	public T delete(Long id) {
		repository.deleteById(id);
		return null;
	}
	
	public List<DTO> convertEntityListToDTO(List<T> entities){
		List<DTO> response = new ArrayList<>();
		entities.forEach(d -> {
			response.add(
					this.converter.from(
							d
							)
					);
		});
		return response;

	}

	public DTO update(Long id, @Valid DTO abstractDTO) {
		T entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não encontrado"));
		
		return converter.from(
				repository.save(converter.to(abstractDTO, entity))
				);
		
	}

	
}
