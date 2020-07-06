package in.nit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nit.model.Product;
import in.nit.service.IProductService;

@RestController
@RequestMapping("/rest/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductRestController {
	@Autowired
	private IProductService service;

	//1. save one product
	@PostMapping("/save")
	public ResponseEntity<String> saveProduct(
			@RequestBody Product product)
	{
		ResponseEntity<String> resp=null;
		try {
			//if Product Id exist
			if(product.getProdId()!=null 
					&& service.isProductExist(product.getProdId())
					)
			{
				resp = new ResponseEntity<String>(
						"Given Id '"+product.getProdId()+"' Data already exist",
						HttpStatus.BAD_REQUEST);

			} else { //Product id not exist
				Integer id=service.saveProduct(product);
				resp = new ResponseEntity<String>(
						"Product '"+id+"' created Successfully!",
						HttpStatus.OK //200
						//HttpStatus.CREATED //201
						);
			}

		} catch (Exception e) {
			resp = new ResponseEntity<String>(
					"Unable to Save Product",
					HttpStatus.INTERNAL_SERVER_ERROR //Our App Got Exception
					);
			e.printStackTrace();
		}
		return resp;
	}

	//2. Get One product by Id
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOneProduct(
			@PathVariable Integer id)
	{
		ResponseEntity<?>  resp = null;
		try {
			//communicate with DB using ID with Serivce Layer
			Optional<Product> opt = service.getOneProduct(id);
			
			if(opt.isPresent()) { //if Product exist
				Product product = opt.get();
				resp = new ResponseEntity<Product>(
						product,
						HttpStatus.OK);
				
			} else { //if product not exist
				resp = new ResponseEntity<String>(
						"Product '"+id+"' Not exist!",
						HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			resp = new ResponseEntity<String>(
					"Unable to fetch Product", 
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return resp;
	}
	
	//3. Get All Products
	@GetMapping("/all")
	public ResponseEntity<?> getAllProducts() {
		ResponseEntity<?> resp = null;
		try {
			
			List<Product> list = service.getAllProducts();
			resp = new ResponseEntity<List<Product>>(list,HttpStatus.OK);
			
		} catch (Exception e) {
			resp = new ResponseEntity<String>(
					"Unable to fetch Products", 
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}

	//4. update product
	@PutMapping("/modify")
	public ResponseEntity<String> updateProduct(
			@RequestBody Product product) 
	{
		ResponseEntity<String> resp = null;
		try {
			//if product exist - then update
			if(product.getProdId()!=null 
					&& service.isProductExist(product.getProdId())
					)
			{
				service.updateProduct(product);
				resp = new ResponseEntity<String>(
						"Product '"+product.getProdId()+"' Updated!",
						HttpStatus.OK);
				
			} else {
				//if product not exist - return error message
				resp = new ResponseEntity<String>(
						"Product '"+product.getProdId()+"' not exist!",
						HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			resp = new ResponseEntity<String>(
					"Unable to update Products", 
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return resp;
	}
	

	//5. Delete one Product
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> removeProduct(
			@PathVariable Integer id) 
	{ 
		ResponseEntity<String> resp  = null;
		try {
			//if Product exist based on Id - DELETE call
			if(service.isProductExist(id)) {
				service.deleteProduct(id);
				resp = new ResponseEntity<String>(
						"Product '"+id+"' Deleted!",
						HttpStatus.OK);
			} else {
				//if given product id not exist
				resp = new ResponseEntity<String>(
						"Product '"+id+"' not exist",
						//HttpStatus.NOT_FOUND
						HttpStatus.BAD_REQUEST
						);
			}
		} catch (Exception e) {
			resp = new ResponseEntity<String>(
					"Unable to Delete Product", 
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return resp;
	}
	
}
