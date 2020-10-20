package demo.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.model.Producto;
import demo.model.Stock;
import demo.repository.StockRepository;


@RestController
@RequestMapping("stock")
public class StockController  {
	

	
	@Qualifier("stockRepository")
    @Autowired
    private final StockRepository repository;
	
	public StockController(@Qualifier("stockRepository") StockRepository repository) {
		this.repository = repository;
	}
	
    @GetMapping("/getAll")
    public Iterable<Stock> getStocks() {
        return repository.findAll();
    }
    
    @PostMapping("/add")
    public Stock newStock(@RequestBody Stock s) {
        return repository.save(s);
    }
    
	 @PutMapping("/update/{id}") public Stock updateStock(@RequestBody Stock s, @PathVariable Long id) {
	        return repository.findById(id)
	                .map(stock -> {
	                	stock.setCantidad(s.getCantidad());
	                    return repository.save(stock);
	                })
	                .orElseGet(() -> {
	                    s.setId(id);
	                    return repository.save(s);
	                });
	 }
	 
	@DeleteMapping("/delete/{id}")
	 void deleteStock(@PathVariable Long id) {
	        repository.deleteById(id);
	    }
 

}
