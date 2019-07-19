package tudelft.discount;

public class DiscountApplier {

    private ProductDao dao;

    public DiscountApplier (ProductDao dao) {
        this.dao = dao;
    }

    public void setNewPrices() {

        for(Product product : dao.all()) {
            if(product.getCategory().equals("BUSINESS")) {
                product.setPrice(Math.round(product.getPrice() * 1.1));
            }
            if(product.getCategory().equals("HOME")) {
                product.setPrice(Math.round(product.getPrice() * 0.9));
            }
        }

    }
}
