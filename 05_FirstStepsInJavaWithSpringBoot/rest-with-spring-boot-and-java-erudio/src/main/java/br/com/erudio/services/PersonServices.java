package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.exeptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;


// objeto injetado em RUNTIME em outras classes
@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;
	
	public List<PersonVO> findAll() {
		
		
		return DozerMapper.parserListObjetc(repository.findAll(), PersonVO.class) ;
	}
	
	public PersonVO findById(Long id) {
		
		logger.info("finding one person");
		
	

		var entity =  repository.findById(id)
			.orElseThrow(()  -> new ResourceNotFoundException("No records found for this ID"));
		return DozerMapper.parserObjetc(entity, PersonVO.class);
	}
	public PersonVO create(PersonVO person) {
		logger.info("Creating one person");
		var entity = DozerMapper.parserObjetc(person, Person.class);
		var vo =  DozerMapper.parserObjetc(repository.save(entity),PersonVO.class);
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one person with V2!");
		var entity = mapper.convertVoToEntity(person);
		var vo =  mapper.convertEntityToVo(repository.save(entity));
		return vo;
	}
	
	public PersonVO uptade(PersonVO person) {
		logger.info("Updating one person");
		
		var entity = repository.findById(person.getId())
			.orElseThrow(()  -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setFirtsName(person.getFirtsName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo =  DozerMapper.parserObjetc(repository.save(entity),PersonVO.class);
		return vo;
	}
	public void delete(Long id) {
		logger.info("deleting one person");
		
		var entity = repository.findById(id)
				.orElseThrow(()  -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
	 
	
}
