import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.bernhoeft.meetings.converter.Converter;
import br.com.bernhoeft.meetings.domain.AbstractEntity;
import br.com.bernhoeft.meetings.dto.AbstractDTO;
import br.com.bernhoeft.meetings.service.AbstractService;



public abstract class AbstractController<
T extends AbstractEntity, 
DTO extends AbstractDTO, 
R extends JpaRepository<T, Long>,
S extends AbstractService<T, DTO, R, Converter<T,DTO>>> {
	
	protected S service;

	public AbstractController(S service) {
		super();
		this.service = service;
	}

	public void getFile(HttpServletResponse response, String filename) {
		try {
            File file = ResourceUtils.getFile("classpath:"+filename);
            InputStream in = new FileInputStream(file);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\""); 
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	@GetMapping
	public List<DTO> getAll(){
		
		return service.getAll();
	}
	
	@GetMapping("/{id}")
	public DTO getOne(@PathVariable("id") Long id){
		return service.get(id);
	}
	
	@PostMapping
	public DTO create(@RequestBody @Valid DTO AbstractDTO) {
		return service.save(AbstractDTO);
		
	}
	
	@PatchMapping("/{id}")
	public AbstractDTO update(@RequestBody DTO AbstractDTO, @PathVariable("id") Long id) {

		return service.update(id,AbstractDTO);
		
	}
	
	@DeleteMapping("/{id}")
	public AbstractDTO delete(@PathVariable("id") Long id) {
		service.delete(id);
		return null;
		
	}
	
	
	@GetMapping("/model")
	public void getModel(HttpServletResponse response){
        getFile(response,"modelo.XLSX");
        
	}
}
