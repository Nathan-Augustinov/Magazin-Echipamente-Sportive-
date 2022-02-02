package org.loose.fis.proiect.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.proiect.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.proiect.model.Product;
import org.loose.fis.proiect.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest
{
    public static final String PRODUCT = "Product";
    public static final String PriceandStock = "10" ;
    @Test
    @DisplayName("Database is initialized and there are no products")
    void testProductDatabaseIsInitializedAndNoProductIsPersisted()
    {
        ProductService.initDatabase();
        assertThat(ProductService.getAllProducts()).isNotNull();
        assertThat(ProductService.getAllProducts()).isEmpty();
    }

    @Test
    @DisplayName("Test if database is closed")
    void testDatabaseIsClosed()
    {
        ProductService.initDatabase();
        ProductService.CloseDatabase();
        assertThat(ProductService.getDatabase().isClosed()).isEqualTo(true);
    }



    @Test
    @DisplayName("Product is successfully persisted to database")
    void testProductIsAddedToDatabase()
    {
        ProductService.addProduct(PRODUCT,PriceandStock,PriceandStock,PRODUCT,PRODUCT);
        ProductService.initDatabase();
        assertThat(ProductService.getAllProducts()).isNotEmpty();
        assertThat(ProductService.getAllProducts()).size().isEqualTo(1);
        Product product = ProductService.getAllProducts().get(0);
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(PRODUCT);
        assertThat(product.getPrice()).isEqualTo(PriceandStock);
        assertThat(product.getStock()).isEqualTo(PriceandStock);
        assertThat(product.getCategory()).isEqualTo(PRODUCT);
        assertThat(product.getCompany()).isEqualTo(PRODUCT);
    }

    @Test
    @DisplayName("Check if product does not already exists in the database")
    void testCheckProductDoesNotAlreadyExists()
    {
        ProductService.addProduct(PRODUCT,PriceandStock,PriceandStock,PRODUCT,PRODUCT);
        assertThat(ProductService.checkProductAlreadyExist(PRODUCT,PriceandStock)).isEqualTo(1);

    }





    @BeforeEach
    void setUp() throws Exception
    {
        FileSystemService.APPLICATION_FOLDER=".test-registration-example";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());

    }

    @BeforeAll
    static void beforeAll()
    {

    }

    @AfterEach
    void tearDown()
    {
        ProductService.CloseDatabase();
    }

    @AfterAll
    static void afterAll()
    {

    }
}