package ar.edu.unq.misCursosUNQ.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.misCursosUNQ.PersonalData;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Repos.PersonalDataRepo;

@Service
public class PersonalDataService {

	@Autowired
	PersonalDataRepo pnRepo;

	public List<PersonalData> getPersonalDatas() {
		List<PersonalData> personalDataList = pnRepo.findAll();

		if(personalDataList.size() > 0) { return personalDataList; } 
		else { return new ArrayList<PersonalData>(); }
	}

	public PersonalData getPersonalDataById(Integer id) throws RecordNotFoundException {
		Optional<PersonalData> personalData = pnRepo.findById(id);

		if(personalData.isPresent()) { return personalData.get(); } 
		else { throw new RecordNotFoundException("PersonalData record not exist for given id"); }
	}
	
	public PersonalData getPersonalDataByDni(Integer dni) throws RecordNotFoundException {
		Optional<PersonalData> personalData = pnRepo.findByDni(dni);

		if(personalData.isPresent()) { return personalData.get(); } 
		else { throw new RecordNotFoundException("PersonalData record not exist for given dni"); }
	}

	@Transactional
	public PersonalData createOrUpdatePersonalData(PersonalData entity) throws RecordNotFoundException {

		if (entity.getPersonalDataId() != null) {

			Optional<PersonalData> optEntity = pnRepo.findById(entity.getPersonalDataId());

			if(optEntity.isPresent()) {

				PersonalData newEntity = optEntity.get();

				newEntity.setDni(entity.getDni());
				newEntity.setFirstName(entity.getFirstName());
				newEntity.setLastName(entity.getLastName());
				newEntity.setEmail(entity.getEmail());
				newEntity.setCellPhone(entity.getCellPhone());

				return pnRepo.save(newEntity);
			}
			throw new RecordNotFoundException("PersonalData record not exist for given id");
		}
		return pnRepo.save(entity);
	}

	@Transactional
	public void deletePersonalDataByDNI(Integer dni) throws RecordNotFoundException {
		Optional<PersonalData> personalData = pnRepo.findByDni(dni);

		if(personalData.isPresent()) { pnRepo.deleteByDni(dni); } 
		else { throw new RecordNotFoundException("PersonalData record not exist for given DNI"); }
	}
	
	@Transactional
	public void deletePersonalDataById(Integer id) throws RecordNotFoundException {
		Optional<PersonalData> personalData = pnRepo.findById(id);

		if(personalData.isPresent()) { pnRepo.deleteById(id);; } 
		else { throw new RecordNotFoundException("PersonalData record not exist for given id"); }
	}
}
