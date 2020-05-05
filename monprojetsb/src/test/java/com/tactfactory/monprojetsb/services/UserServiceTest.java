package com.tactfactory.monprojetsb.services;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import com.tactfactory.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.repositories.ProductRepository;
import com.tactfactory.monprojetsb.repositories.UserRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@EntityScan(basePackages="com.tactfactory.monprojetsb")
@ComponentScan(basePackages="com.tactfactory.monprojetsb")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Clear Database before each test
     */
    @Before
    public void clear() {
        this.productRepository.deleteAll();
        this.userRepository.deleteAll();
    }

    /**
     * Test que linsertion dun lment ajoute bien un enregistrement dans la base de donnes
     */
    @Test
    public void TestInsertOne() {
        Long before = userRepository.count();
        userService.save(new User());
        Long after = userRepository.count();

        assertEquals(before + 1, after);
    }

    /**
     * Test que linsertion dun lment na pas altrer les donnes de lobjet sauvegard
     */
    @Test
    public void TestInsertUser() {
        User userBase = new User(null, "Tardif", "Dylan", new ArrayList<Product>());
        Long id = userService.save(userBase).getId();
        User userFetch = userRepository.getUserById(id);

        assertTrue(compare(userBase, userFetch));
    }

    /**
     * Test que la mise  jour dun lment na pas altrer les donnes de lobjet sauvegard
     */
    @Test
    public void TestUpdateUser() {
        // Create User
        User userBase = new User(null, "Tardif", "Dylan", new ArrayList<Product>());
        Long id = userService.save(userBase).getId();

        //Create Product 1
        Product product1 = new Product(null, "product1",(float) 10);
        Long p1 = productRepository.save(product1).getId();
        Product productFetch1 = productRepository.getProductById(p1);

        // Create product 2
        Product product2 = new Product(null, "product2",(float) 10);
        Long p2 = productRepository.save(product2).getId();
        Product productFetch2 = productRepository.getProductById(p2);

        // Add products 1 and 2 to products
        List<Product> products = new ArrayList<Product>();
        products.add(productFetch1);
        products.add(productFetch2);

        // Get user and set products
        User userFetch = userRepository.getUserById(id);
        userFetch.setProducts(products);

        // Update user and get id to check modifications
        Long idUpdated = userService.save(userFetch).getId();
        User userFetchUpdated = userRepository.getUserById(idUpdated);

        assertTrue(compare(userFetch, userFetchUpdated));
    }

    /**
     * Test quun lment est rcupr avec les bonnes donnes
     */
    @Test
    public void TestGetUser() {
        User userBase = new User(null , "Tardif", "Dylan", new ArrayList<Product>());
        Long id = userRepository.save(userBase).getId();
        User userFetch = userService.getUserById(id);

        assertTrue(compare(userBase, userFetch));
    }

    /**
     * Test quune liste est rcupr avec les bonnes donnes
     */
    @Test
    public void TestGetUsers() {
        List<User> users = new ArrayList<User>();
        User user1 = new User(null, "Tardif", "Dylan", new ArrayList<Product>());
        users.add(user1);
        User user2 = new User(null, "Gantois", "Emilien", new ArrayList<Product>());
        users.add(user2);

        userService.saveList(users);

        List<User> usersFetch = userService.findAll();

        for (int i = 0; i < usersFetch.size(); i++) {
            assertTrue(compare(users.get(i), usersFetch.get(i)));
        }
    }

    /**
     * Test que la suppression dun lment dcrmente le nombre denregistrement prsent
     */
    @Test
    public void TestDeleteOne() {
        User userTemp = new User();
        userService.save(userTemp);
        Long before = userRepository.count();
        userService.delete(userTemp);
        Long after = userRepository.count();

        assertEquals(before - 1, after);
    }

    /**
     * Test que la suppression dun lment supprime bien le bon lment
     */
    @Test
    public void TestDeleteUser() {
        User userBase = new User(null , "Tardif", "Dylan", new ArrayList<Product>());
        Long id = userRepository.save(userBase).getId();
        userService.delete(userBase);

        User deletedUser = userService.getUserById(id);
        assertNull(deletedUser);
    }

    /**
     * Cration dun nouvel lment avec un sous lment non existant en comptant
     */
    @Test
    public void TestCreaEntitePrimitive1() {
        Long productBefore = productRepository.count();

        List<Product> products = new ArrayList<Product>();
        products.add(new Product());
        User userBase = new User(null, "Tardif", "Dylan", products);

        userService.save(userBase).getId();
        Long productAfter = productRepository.count();

        assertEquals(productBefore, productAfter);
    }

    /**
     * Cration dun nouvel lment avec un sous lment non existant en regardant les valeur
     */
    @Test
    public void TestCreaEntitePrimitive2() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product());
        User userBase = new User(null, "Tardif", "Dylan", products);

        Long idUpdated = userService.save(userBase).getId();
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.flush());
    }

    /**
     * Cration dun nouvel lment avec un sous lment existant en comptant
     */
    @Test
    public void TestCreaEntitePrimitive3() {
        List<Product> products = new ArrayList<Product>();
        Product p1 = new Product(null, "product1", (float) 10);
        Long idP1 = productRepository.save(p1).getId();
        p1.setId(idP1);
        products.add(p1);
        User userBase = new User(null, "Tardif", "Dylan", products);

        Long before = userRepository.count();
        userService.save(userBase).getId();
        Long after = userRepository.count();

        assertEquals(before + 1, after);
    }

    /**
     * Cration dun nouvel lment avec un sous lment existant en regardant les valeur
     */
    @Test
    public void TestCreaEntitePrimitive4() {
        List<Product> products = new ArrayList<Product>();
        Product p1 = new Product(null, "product1", (float) 10);
        Long idP1 = productRepository.save(p1).getId();
        p1.setId(idP1);
        products.add(p1);
        User userBase = new User(null, "Tardif", "Dylan", products);

        Long idUpdated = userService.save(userBase).getId();
        User userFetch = userRepository.getUserById(idUpdated);

        assertTrue(compare(userBase, userFetch));
    }

    /**
     * Cration dun nouvel lment avec un sous lment  mettre  jour en comptant
     */
    @Test
    public void TestCreaEntitePrimitive5() {
        List<Product> products = new ArrayList<Product>();
        Product p1 = new Product(null, "product1", (float) 10);
        Long idP1 = productRepository.save(p1).getId();
        p1.setId(idP1);
        products.add(p1);
        products.get(0).setPrice((float) 12);
        User userBase = new User(null, "Tardif", "Dylan", products);

        Long before = userRepository.count();
        userService.save(userBase).getId();
        Long after = userRepository.count();

        assertEquals(before + 1, after);
    }

    /**
     * Cration dun nouvel lment avec un sous lment  mettre  jour en regardant les valeur
     */
    @Test
    public void TestCreaEntitePrimitive6() {
        List<Product> products = new ArrayList<Product>();
        Product p1 = new Product(null, "product1", (float) 10);
        Long idP1 = productRepository.save(p1).getId();
        p1.setId(idP1);
        products.add(p1);
        products.get(0).setPrice((float) 12);
        User userBase = new User(null, "Tardif", "Dylan", products);

        Long idUpdated = userService.save(userBase).getId();
        User userFetch = userRepository.getUserById(idUpdated);

        assertTrue(compare(userBase, userFetch));
    }

    /**
     * Cration dun nouvel lment avec un sous lment  enlever en comptant
     */
    @Test
    public void TestCreaEntitePrimitive7() {
        Long before = userRepository.count();

//      create user avec 3 produits en base
        List<Product> products = new ArrayList<Product>();
        Product p1 = new Product(null, "product1", (float) 10);
        Long idP1 = productRepository.save(p1).getId();
        p1.setId(idP1);
        products.add(p1);

        Product p2 = new Product(null, "product2", (float) 10);
        Long idP2 = productRepository.save(p2).getId();
        p2.setId(idP2);
        products.add(p2);

        Product p3 = new Product(null, "product3", (float) 10);
        Long idP3 = productRepository.save(p3).getId();
        p3.setId(idP3);
        products.add(p3);

        User userBase = new User(null, "Tardif", "Dylan", products);
        Long idCreate = userService.save(userBase).getId();

//      recup
        User userFetch = userRepository.getUserById(idCreate);

//      retire 1 produit
        userFetch.getProducts().remove(0);

//      update
        userService.save(userFetch).getId();

//      recup + check
        Long after = userRepository.count();

        assertEquals(before + 1, after);
    }

    /**
     * Cration dun nouvel lment avec un sous lment  enlever en regardant les valeur
     */
    @Test
    public void TestCreaEntitePrimitive8() {
//      create user avec 3 produits en base
        List<Product> products = new ArrayList<Product>();
        Product p1 = new Product(null, "product1", (float) 10);
        Long idP1 = productRepository.save(p1).getId();
        System.out.println(idP1);
        p1.setId(idP1);
        products.add(p1);

        Product p2 = new Product(null, "product2", (float) 10);
        Long idP2 = productRepository.save(p2).getId();
        System.out.println(idP2);
        p2.setId(idP2);
        products.add(p2);

        Product p3 = new Product(null, "product3", (float) 10);
        Long idP3 = productRepository.save(p3).getId();
        System.out.println(idP3);
        p3.setId(idP3);
        products.add(p3);

        User userBase = new User(null, "Tardif", "Dylan", products);
        Long idCreate = userService.save(userBase).getId();
        System.out.println(idCreate);

//      recup
        User userFetch = userService.getUserById(idCreate);
        System.out.println(userFetch);

//      retire 1 produit
        List<Product> productsUptaded = userFetch.getProducts();
        System.out.println(productsUptaded.size());
        productsUptaded.remove(0);
        System.out.println(productsUptaded.size());
        userFetch.setProducts(productsUptaded);

//      update
        userService.save(userFetch).getId();

//      recup + check
        User userUpdated = userRepository.getUserById(idCreate);

        assertTrue(compare(userUpdated, userFetch));
    }

    public Boolean compare(User user1, User user2) {
        boolean result = true;

        if (!user1.getId().equals(user2.getId())) {
            result = false;
            System.out.println("id: " + user1.getId() + " " + user2.getId());
        }
        if (!user1.getFirstname().equals(user2.getFirstname())) {
            result = false;
            System.out.println("firstname: " + user1.getFirstname() + " " + user2.getFirstname());
        }
        if (!user1.getLastname().equals(user2.getLastname())) {
            result = false;
            System.out.println("lastname: " + user1.getLastname() + " " + user2.getLastname());
        }
        if (!user1.getProducts().equals(user2.getProducts())) {
            result = false;
            System.out.println("products: " + user1.getProducts() + " " + user2.getProducts());
        }

        return result;
    }
}
