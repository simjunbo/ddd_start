package dao;

import domain.CategoryId;
import domain.Product;
import domain.ProductId;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaProductRepository implements ProductRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Product product) {
		entityManager.persist(product);
	}

	@Override
	public Product findById(ProductId id) {
		return entityManager.find(Product.class, id);
	}

	@Override
	public void remove(Product product) {
		entityManager.remove(product);
	}

	@Override
	public List<Product> findAll() {
		TypedQuery<Product> query = entityManager.createQuery(
				"select p from Product p order by p.id.id desc",
				Product.class);
		return query.getResultList();
	}

	@Override
	public List<Product> findByCategoryId(CategoryId categoryId, int page, int size) {
		TypedQuery<Product> query = entityManager.createQuery(
				"select p from Product p where :catId member of p.categoryIds order by p.id.id desc",
				Product.class);
		query.setParameter("catId", categoryId);
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		return query.getResultList();
	}

	@Override
	public long countsByCategoryId(CategoryId categoryId) {
		TypedQuery<Long> query = entityManager.createQuery(
				"select count(p) from Product p where :catId member of p.categoryIds",
				Long.class);
		query.setParameter("catId", categoryId);
		return query.getSingleResult().longValue();
	}

	@Override public ProductId nextId() {
		return null;
	}
}
