package com.estudo.springstudy.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.estudo.springstudy.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	
	public void preencherPagamentoComBoleto(PagamentoComBoleto boleto, Date instantePagamento) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		boleto.setDataVencimento(cal.getTime());
	}
}
