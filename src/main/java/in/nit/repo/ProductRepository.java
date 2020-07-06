package in.nit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nit.model.Product;

public interface ProductRepository 
	extends JpaRepository<Product, Integer>
{

}
