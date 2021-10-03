package br.edu.unifacef.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unifacef.dao.PagamentoDao;
import br.edu.unifacef.model.Lance;
import br.edu.unifacef.model.Pagamento;


@Service
public class GeradorDePagamento {	
	

		@Autowired
		private PagamentoDao pagamentos;
		
		
		
		@Autowired
		public GeradorDePagamento(PagamentoDao pagamentos) {		
			this.pagamentos = pagamentos;
		}




		public void gerarPagamento(Lance lanceVencedor) {
			LocalDate vencimento = LocalDate.now().plusDays(1);
			Pagamento pagamento = new Pagamento(lanceVencedor, vencimento);
			this.pagamentos.salvar(pagamento);
		}

}
	
