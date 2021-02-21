package com.example.truemart.service;

import com.example.truemart.DTO.CartDTO;
import com.example.truemart.DTO.CartItemDTO;
import com.example.truemart.DTO.WishlistDTO;
import com.example.truemart.DTO.WishlistItemDTO;
import com.example.truemart.entity.*;
import com.example.truemart.repository.WishlistRepository;
import com.example.truemart.tools.CurrencyTool;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WishlistService {
    private final UserService userService;
    private final ProductService productService;
    private final WishlistRepository wishlistRepository;

    public WishlistService(UserService userService, ProductService productService, WishlistRepository wishlistRepository) {
        this.userService = userService;
        this.productService = productService;
        this.wishlistRepository = wishlistRepository;
    }

    public WishlistDTO getWistlistDTO(String identity) {
        UserTrueMart user = userService.getUserByEmail(identity);
        if (user == null) {
            return new WishlistDTO();
        }
        return convertWishlistDTOFromUser(user);
    }

    public WishlistDTO convertWishlistDTOFromUser(UserTrueMart user) {
        //convert to WishlistDTO from user.wishlist
        Wishlist c = user.getWishlist();
        WishlistDTO dto = new WishlistDTO();


        for (WishlistItem i : c.getWishlistItems()) {

            WishlistItemDTO itemDTO = new WishlistItemDTO(
                    i.getProductsAndSizes().getProduct().getId(),
                    i.getProductsAndSizes().getProduct().getName(),
                    i.getProductsAndSizes().getProduct().getImages().get(0).getUrl(),
                    i.getProductsAndSizes().isStocked(),
                    String.valueOf(i.getProductsAndSizes().getPrice()),
                    i.getProductsAndSizes().getId(),
                    i.getProductsAndSizes().getProduct().getDiscount(),null,
                    i.getProductsAndSizes().getStock()
            );
            if (i.getProductsAndSizes().getProduct().isDiscountable()) {
                String price = CurrencyTool.getDiscountPrice(i.getProductsAndSizes().getPrice(),i.getProductsAndSizes().getProduct().getDiscount());
                itemDTO.setPrice_after_discount(price);
            } else  {
                itemDTO.setPrice_after_discount(CurrencyTool.getPrice(i.getProductsAndSizes().getPrice()));
            }


            dto.getWishlistItemDTOS().add(itemDTO);
        }


        return dto;
    }

    public Wishlist getWishlistFromUser(String email) {
        UserTrueMart user = userService.getUserByEmail(email);
        if (user != null) {
            return user.getWishlist();
        }

        return  null;
    }

    public Wishlist save(Wishlist list) {
        return wishlistRepository.save(list);
    }

    public void updateWishlishFromWishlistDTO(WishlistDTO dto, String email) {
        Wishlist wishlist = convertWishlishDTOToWishlish(dto);

        UserTrueMart user = userService.getUserByEmail(email);
        user.setWishlist(wishlist);

        userService.saveUser(user);
    }

    private Wishlist convertWishlishDTOToWishlish(WishlistDTO dto) {
        Wishlist cart = new Wishlist();

        for(WishlistItemDTO itemDTO : dto.getWishlistItemDTOS()) {
            if(itemDTO.getAmount() != 0 ) {
                WishlistItem item = new WishlistItem();
                item.setProductsAndSizes(productService.findProductsAndSizesById(itemDTO.getPns_id()));

                cart.addWishlistItem(item);
            }
        }

        return cart;
    }
}
