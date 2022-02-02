package org.loose.fis.proiect.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.proiect.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.proiect.model.Product;
import org.loose.fis.proiect.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;

public class ProductService {

    private static ObjectRepository<Product> productRepository;
    private static Nitrite database;

    public static void initDatabase()
    {
        FileSystemService.initDirectory();
        database = Nitrite.builder()
                .filePath(getPathToFile("products.db").toFile())
                .openOrCreate("test", "test");

        productRepository = database.getRepository(Product.class);
    }
    public static void addProduct(String name, String price, String stock,String category,String company)
    {
        initDatabase();
        productRepository.insert(new Product(name, price, stock,category,company));
        database.close();
    }
    public static int checkProductAlreadyExist(String name,String stock)
    {
        initDatabase();
        int k=0;
        for (Product product : productRepository.find()) {
            if (Objects.equals(name, product.getName()))
            {
                String st=product.getStock();
                product.setStock(String.valueOf(Integer.parseInt(st)+Integer.parseInt(stock)));
                productRepository.update(product);
                k=1;
            }
        }
        database.close();
        return k;
    }
    public static void CloseDatabase()
    {
        database.close();
    }

    public static Nitrite getDatabase()
    {
        return database;
    }

    public static List<Product> getAllProducts()
    {
        return productRepository.find().toList();
    }
}
