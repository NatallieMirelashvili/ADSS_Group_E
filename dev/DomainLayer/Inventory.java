package DomainLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Inventory extends AStock{
    public Inventory(HashMap<String, HashMap<String, HashMap<Double, ArrayList<Product>>>> myStock, int amountIn) {
        super(myStock, amountIn);
    }

    public void setSalePrice(salePrice mySalePrice, String cat, String subCat , Double size, Date from, Date to, double discount) {
        salePrice newSale = new salePrice(from, to, discount);
        HashMap<String, HashMap<Double, ArrayList<Product>>> subCatToChange = myStock.get(cat);
        if (subCat.equals("")) {
            for (String mysubCat : subCatToChange.keySet()) {
                HashMap<Double, ArrayList<Product>> sizeToChange = myStock.get(cat).get(mysubCat);
                for (Double mysize : sizeToChange.keySet()) {
                    ArrayList<Product> productToChange = myStock.get(cat).get(mysubCat).get(mysize);
                    for (Product product : productToChange)
                        product.setMySalePrice(newSale);
                }
            }
            return;
        }
        if (size == 0.0) {
            HashMap<Double, ArrayList<Product>> sizeToChange = myStock.get(cat).get(subCat);
            for (Double mysize : sizeToChange.keySet()) {
                ArrayList<Product> productToChange = myStock.get(cat).get(subCat).get(mysize);
                for (Product product : productToChange)
                    product.setMySalePrice(newSale);
            }
            return;
        }
            ArrayList<Product> productToChange = myStock.get(cat).get(subCat).get(size);
            for (Product product : productToChange)
                product.setMySalePrice(newSale);
        }

    public void setSalePrice(salePrice mySalePrice, String cat, String subCat , Date from, Date to, double discount) {
        setSalePrice( mySalePrice,  cat,  subCat ,0.0,  from,  to,  discount);
        }
    public void setSalePrice(salePrice mySalePrice, String cat , Date from, Date to, double discount) {
        setSalePrice( mySalePrice,  cat,  "" ,0.0,  from,  to,  discount);
    }

    }

