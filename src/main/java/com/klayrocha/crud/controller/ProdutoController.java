package com.klayrocha.crud.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klayrocha.crud.VO.ProdutoVO;
import com.klayrocha.crud.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
	
	private final ProdutoService produtoService;
	private final PagedResourcesAssembler<ProdutoVO> assembler;
	
	public ProdutoController(@Autowired ProdutoService produtoService, 
							 @Autowired PagedResourcesAssembler<ProdutoVO> assembler) {
		super();
		this.produtoService = produtoService;
		this.assembler = assembler;
	}
	
	@GetMapping(value="/{id}",produces = {"application/json","application/xml","application/x-yaml"})
	public ProdutoVO findById(@PathVariable("id") Long id) {
		
		ProdutoVO produtoVO = produtoService.findById(id);
		produtoVO.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
		
		return produtoVO;
	}
	
	@GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
	public ResponseEntity<?> findAll(
			@RequestParam(value="page",defaultValue = "0") int page,
			@RequestParam(value="limit",defaultValue = "12") int limit,
			@RequestParam(value="direction",defaultValue = "asc") String direction
	) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit,Sort.by(sortDirection,"nome"));
		
		Page<ProdutoVO> produtos = produtoService.findAll(pageable);
		produtos.stream().forEach(p -> p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel()));
		
		PagedModel<EntityModel<ProdutoVO>> pagedModel = assembler.toModel(produtos);
		
		return ResponseEntity.ok(pagedModel);
	}
	
	@PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
				 consumes = {"application/json","application/xml","application/x-yaml"})
	public ProdutoVO create(@RequestBody ProdutoVO produtoVO) {
		ProdutoVO pVO = produtoService.create(produtoVO);
		pVO.add(linkTo(methodOn(ProdutoController.class).findById(pVO.getId())).withSelfRel());
		return pVO;
	}
	
	@PutMapping(produces = {"application/json","application/xml","application/x-yaml"},
			    consumes = {"application/json","application/xml","application/x-yaml"})
	public ProdutoVO update(@RequestBody ProdutoVO produtoVO) {
		ProdutoVO pVO = produtoService.update(produtoVO);
		pVO.add(linkTo(methodOn(ProdutoController.class).findById(pVO.getId())).withSelfRel());
		return pVO;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		produtoService.delete(id);
		return ResponseEntity.ok().build();
	}

}
