/*
 * package org.javaworld.cmsbackend.dao;
 * 
 * import static org.junit.Assert.assertNotNull; import static
 * org.junit.Assert.assertTrue;
 * 
 * import org.javaworld.cmsbackend.entity.Category; import
 * org.javaworld.cmsbackend.entity.Product; import org.junit.Test; import
 * org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
 * import
 * org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.
 * Replace; import
 * org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest; import
 * org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager; import
 * org.springframework.test.context.junit4.SpringRunner;
 * 
 * @RunWith(SpringRunner.class)
 * 
 * @DataJpaTest
 * 
 * @AutoConfigureTestDatabase(replace = Replace.NONE) public class
 * ProductRepositoryIntegrationTest {
 * 
 * @Autowired private TestEntityManager entityManager;
 * 
 * @Autowired private ProductRepository productRepository;
 * 
 * 
 * @Test public void testSaveProduct() {
 * 
 * Category c = new Category(); c.setId(2);
 * 
 * Product product = new Product(); product.setName("shawerma");
 * product.setActive(true); product.setImageName("test.png");
 * product.setPrice(20); product.setCategory(c); Product savedProduct =
 * productRepository.save(product); assertNotNull(savedProduct.getId()); }
 * 
 * @Test public void whenFindByName_thenReturnProduct() {
 * 
 * // given Product product = new Product(); product.setName("shawerma");
 * product.setActive(true); product.setImageName("xx"); product.setPrice(20);
 * entityManager.persist(product); entityManager.flush();
 * 
 * // when Product found = productRepository.findByName(product.getName());
 * 
 * // then assertTrue(found.getName().equals(product.getName()));
 * 
 * }
 * 
 * }
 */