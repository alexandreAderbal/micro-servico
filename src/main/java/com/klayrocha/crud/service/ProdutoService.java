package com.klayrocha.crud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.klayrocha.crud.VO.ProdutoVO;
import com.klayrocha.crud.entity.Produto;
import com.klayrocha.crud.exception.ResorceNotFoundException;
import com.klayrocha.crud.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;

	public ProdutoService(@Autowired ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;	
	}
	
	public ProdutoVO create(ProdutoVO produtoVO) {
		
		Produto produto = produtoRepository.save(Produto.create(produtoVO));
		
		return ProdutoVO.create(produto);
	}
	
	public Page<ProdutoVO> findAll(Pageable pageable){
		var page = produtoRepository.findAll(pageable);
		
		return page.map(this::convertToProdutoVO);
	}
	
	private ProdutoVO convertToProdutoVO(Produto produto) {
		return ProdutoVO.create(produto);
	}
	
	public ProdutoVO findById(Long id) {
		var entity = produtoRepository.findById(id)
						.orElseThrow(() -> new ResorceNotFoundException("No records found for this Id"));
		
		return ProdutoVO.create(entity);
	}
	
	public ProdutoVO update(ProdutoVO produtoVO) {
		
		final Optional<Produto> optionalP = produtoRepository.findById(produtoVO.getId());
		
		if(!optionalP.isEmpty()) new ResorceNotFoundException("No records found for this Id");
		
		return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
	}
	
	public void delete(Long id) {
		
		var entity = produtoRepository.findById(id)
			.orElseThrow(() -> new ResorceNotFoundException("No records found for this Id"));

		produtoRepository.delete(entity);
	}
	
}
