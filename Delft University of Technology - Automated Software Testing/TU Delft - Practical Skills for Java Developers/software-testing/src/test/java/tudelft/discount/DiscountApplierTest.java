package tudelft.discount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class DiscountApplierTest {

    private DiscountApplier discount;
    private ProductDao dao;

    @BeforeEach
    public void initialize() {
        Product p1 = new Product("Pen", 100, "HOME");
        Product p2 = new Product("Pencil", 100, "BUSINESS");

        //this.dao = Mockito.mock(ProductDao.class);

        this.dao = new ProductDao() {
            @Override
            public List <Product> all() {
                List<Product> newList = new LinkedList <>();
                newList.add(p1);
                newList.add(p2);
                return newList;
            }
        };

        this.discount = new DiscountApplier(dao);
    }

    @Test
    public void test01() {
        discount.setNewPrices();
        for (Product p: dao.all()) {
            if (p.getCategory().equals("HOME")) {
                Assertions.assertEquals(p.getPrice(), 90);
            } else if (p.getCategory().equals("BUSINESS")) {
                Assertions.assertEquals(p.getPrice(), 110);
            }
        }
    }


    @Test
    public void test02() {
        discount.setNewPrices();
        discount.setNewPrices();
        for (Product p: dao.all()) {
            if (p.getCategory().equals("HOME")) {
                Assertions.assertEquals(p.getPrice(), 81);
            } else if (p.getCategory().equals("BUSINESS")) {
                Assertions.assertEquals(p.getPrice(), 121);
            }
        }
    }

    @Test
    public void test03() {
        Product p1 = new Product("Pen", 0, "HOME");
        Product p2 = new Product("Pencil", 0, "BUSINESS");

        this.dao = new ProductDao() {
            @Override
            public List <Product> all() {
                List<Product> newList = new LinkedList <>();
                newList.add(p1);
                newList.add(p2);
                return newList;
            }
        };

        this.discount = new DiscountApplier(dao);

        discount.setNewPrices();
        for (Product p: dao.all()) {
            if (p.getCategory().equals("HOME")) {
                Assertions.assertEquals(p.getPrice(), 0);
            } else if (p.getCategory().equals("BUSINESS")) {
                Assertions.assertEquals(p.getPrice(), 0);
            }
        }
    }

}
