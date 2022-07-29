package Menu;
import Product.*;

import java.io.BufferedReader;
import java.io.FileReader;


public class MainMenu {
    private ProductService[] ProductList;

    public MainMenu() throws Exception {
        this.ProductList = new ProductService[0];
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader("Repo/Products.csv"));
        while (line != null)
        {
            if((line = br.readLine()) == null){
                continue;
            }
            String[] singleItem = line.split(",");
            ProductService product = new ProductService (Integer.parseInt(singleItem[0]), singleItem[1], singleItem[2], singleItem[3],Float.parseFloat(singleItem[4]));
            addToProductList(product);
        }
    }

    public ProductService[] getProductList() {
        return ProductList;
    }

    public void addToProductList(ProductService product){
        ProductService[] copy = new ProductService[this.ProductList.length + 1];
        System.arraycopy(this.ProductList, 0, copy, 0, this.ProductList.length);
        this.ProductList = copy;
        for (int i = 0; i < this.ProductList.length; i++) {
            if (this.ProductList[i] == null){
                this.ProductList[i] = product;
                return;
            }
        }
    }
}
