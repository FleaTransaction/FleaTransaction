package cn.fleatransaction.util;

import cn.fleatransaction.entity.ProDemand;
import cn.fleatransaction.entity.Product;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ListUtils {
    public static List<Product> distinctByProductId(List<Product> products) {
        Set<Product> set = new TreeSet<Product>(new Comparator<Product>() {
            @Override
            public int compare(Product a, Product b) {
                int compareToResult = 1;
                if(a.getProductId() == b.getProductId()) {
                    compareToResult = 0;
                }
                return compareToResult;
            }
        });
        set.addAll(products);
        return new ArrayList<Product>(set);
    }

    public static List<ProDemand> distinctByDemand(List<ProDemand> products) {
        Set<ProDemand> set = new TreeSet<ProDemand>(new Comparator<ProDemand>() {
            @Override
            public int compare(ProDemand a, ProDemand b) {
                int compareToResult = 1;
                if(a.getDemandId() == b.getDemandId()) {
                    compareToResult = 0;
                }
                return compareToResult;
            }
        });
        set.addAll(products);
        return new ArrayList<ProDemand>(set);
    }
}
