package br.edu.unifacef.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.edu.unifacef.dao.PagamentoDao;
import br.edu.unifacef.model.Lance;
import br.edu.unifacef.model.Leilao;
import br.edu.unifacef.model.Pagamento;
import br.edu.unifacef.model.Usuario;



class GeradorDePagamentoTest {

private GeradorDePagamento gerador;
	
	@Mock
	private PagamentoDao pagamentoDao;
	
	//Captura um objeto de uma classe a ser testada com Mockito
	@Captor
	private ArgumentCaptor<Pagamento> captor;
	
	
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		this.gerador = new GeradorDePagamento(pagamentoDao);		
	}	

	@Test
	void deveriaCriarPagamentoParaVencedorDoLeilao() {
		Leilao leilao = leilao();		
		Lance vencedor = leilao.getLanceVencedor();
		gerador.gerarPagamento(vencedor);
		
		Mockito.verify(pagamentoDao).salvar(captor.capture());
		
		Pagamento pagamento = captor.getValue();
		Assert.assertEquals(LocalDate.now().plusDays(1), pagamento.getVencimento());
		Assert.assertEquals(vencedor.getValor(), pagamento.getValor());
		Assert.assertFalse(pagamento.getPago());
		Assert.assertEquals(vencedor.getUsuario(), pagamento.getUsuario());
		Assert.assertEquals(leilao, pagamento.getLeilao());
		
	}
	
	private Leilao leilao(){
		
			
		Leilao leilao = new Leilao ("Celular",new BigDecimal("500"),new Usuario("Fulano"));		
		Lance lance = new Lance (new Usuario ("Ciclano"),new BigDecimal("900"));		
		
		leilao.propoe(lance);
		leilao.setLanceVencedor(lance);
			
		
		return leilao;	
		
		
	}

}
