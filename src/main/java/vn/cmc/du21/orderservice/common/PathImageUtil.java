package vn.cmc.du21.orderservice.common;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import vn.cmc.du21.inventoryservice.presentation.external.controller.ProductController;

public class PathImageUtil {
    private PathImageUtil(){super();}

    public static String getPathImage(String fileName){
            return MvcUriComponentsBuilder.fromMethodName(ProductController.class,
                    "readDetailFile", fileName).toUriString();
    }
}
