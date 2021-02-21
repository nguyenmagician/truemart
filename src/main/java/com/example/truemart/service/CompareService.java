package com.example.truemart.service;

import com.example.truemart.DTO.CompareDTO;
import com.example.truemart.DTO.CompareItemDTO;
import com.example.truemart.entity.Compare;
import com.example.truemart.entity.CompareItem;
import com.example.truemart.entity.ProductsAndSizes;
import com.example.truemart.entity.UserTrueMart;
import com.example.truemart.repository.CompareRepository;
import com.example.truemart.tools.CurrencyTool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompareService {
    private final UserService userService;
    private final ProductService productService;
    private final CompareRepository compareRepository;

    public CompareService(UserService userService, ProductService productService, CompareRepository compareRepository) {
        this.userService = userService;
        this.productService = productService;
        this.compareRepository = compareRepository;
    }

    public Compare save(Compare compare){
        return compareRepository.save(compare);
    }

    public Compare getCompareFromUser(String email) {
        UserTrueMart user = userService.getUserByEmail(email);
        if (user != null) {
            return user.getCompare();
        }

        return  null;
    }

    public CompareDTO getCompareDTO(String user_email) {
        Compare compare = getCompareFromUser(user_email);

        if(compare == null) {
            return  new CompareDTO();
        }

        CompareDTO dto = new CompareDTO();
        for(CompareItem item : compare.getCompareItems()) {
            ProductsAndSizes pns = item.getProductsAndSizes();

            CompareItemDTO itemDTO = new CompareItemDTO();
            itemDTO.setProduct_name(pns.getProduct().getName());
            itemDTO.setShort_detail(pns.getProduct().getShort_detail());
            itemDTO.setStockable(pns.isStocked());
            if(pns.getProduct().isDiscountable()) {
                itemDTO.setPrice(CurrencyTool.getDiscountPrice(pns.getPrice(),pns.getProduct().getDiscount()));
            }else {
                itemDTO.setPrice(CurrencyTool.getPrice(pns.getPrice()));
            }
            itemDTO.setImage_url(pns.getProduct().getImages().get(0).getUrl());
            itemDTO.setId(pns.getProduct().getId());
            itemDTO.setUrl("/product?id="+itemDTO.getId());

            dto.getCompareItemDTOList().add(itemDTO);
        }

        return dto;
    }

    public boolean removeItem(Long product_delete_id, String email) {
        UserTrueMart user = userService.getUserByEmail(email);
        boolean result = false;

        if(user == null) {
            return false;
        }

        Compare compare = user.getCompare();
        if (compare == null) {
            return false;
        }

        List<CompareItem> list = compare.getCompareItems();

        for(CompareItem compareItem : list) {
            if(compareItem.getId() == product_delete_id) {
              if (compare.getCompareItems().remove(compareItem))
                  result = true;
            }
        }

        user.setCompare(compare);
        userService.saveUser(user);

        return result;
    }
}
