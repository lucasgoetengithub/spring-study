package com.estudo.springstudy.service.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.estudo.springstudy.domain.enums.TipoCliente;
import com.estudo.springstudy.dto.ClienteNewDTO;
import com.estudo.springstudy.resources.exceptions.FieldMessage;
import com.estudo.springstudy.service.validations.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Override
	public void initialize(ClienteInsert ann) {	
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCode()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CPF inválido"));
		} else if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCode()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CNPJ inválido"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}