package br.com.erudio.mapper.custom;

import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {
    public PersonDTOV2 converterEntityToDTO(Person person){
        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setNome(person.getNome());
        dto.setSobreNome(person.getSobreNome());
        dto.setEmail(person.getEmail());
        dto.setEndereco(person.getEndereco());
        dto.setDataAniversario(new Date());
        return dto;
    }
    public Person converterDTOToEntity(PersonDTOV2 dto){
        Person entity = new Person();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setSobreNome(dto.getSobreNome());
        entity.setEmail(dto.getEmail());
        entity.setEndereco(dto.getEndereco());
        //entity.setDataAniversario(new Date());
        return entity;
    }
}
