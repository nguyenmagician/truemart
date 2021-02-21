package com.example.truemart.service;

import com.example.truemart.DTO.CartDTO;
import com.example.truemart.DTO.CartItemDTO;
import com.example.truemart.entity.Cart;
import com.example.truemart.entity.CartItem;
import com.example.truemart.entity.ProductsAndSizes;
import com.example.truemart.entity.UserTrueMart;
import com.example.truemart.repository.CartRepository;
import com.example.truemart.tools.CurrencyTool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final UserService userService;
    private final ProductService productService;
    private final CartRepository cartRepository;

    public CartService(UserService userService, ProductService productService, CartRepository cartRepository) {
        this.userService = userService;
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    public CartDTO getCartDTO(String identity) {
        UserTrueMart user = userService.getUserByEmail(identity);
        if (user == null) {
            return  new CartDTO();
        }
        return convertCartDTOFromUser(user);
    }

    public void updateCartFromCartDTO(CartDTO cartDTO,String identity) {
        Cart cart = convertCartDTOToCart(cartDTO);
        
        UserTrueMart user = userService.getUserByEmail(identity);
        user.setCart(cart);

        userService.saveUser(user);
    }

    public Cart convertCartDTOToCart(CartDTO cartDTO) {
        Cart cart = new Cart();

        for(CartItemDTO itemDTO : cartDTO.getCarts()) {
            if(itemDTO.getQuantities() == 0) {
                continue;
            }
            CartItem item = new CartItem();
            Optional<ProductsAndSizes> pns = productService.SingleProductProvider(itemDTO.getId(),itemDTO.getSize());
            if(pns.isPresent()) {
                item.setProductsAndSizes(pns.get());
            }
            item.setQuantityPurchase(itemDTO.getQuantities());
            cart.addCartItem(item);

        }

        return cart;
    }

    public CartDTO convertCartDTOFromUser(UserTrueMart user) {
        //convert to CartDTO from user.cart
        Cart c = user.getCart();
        CartDTO dto = new CartDTO();
        convertCartItemToCartItemDTO(dto,c);

        dto.setAmount(c.getCartItems().size());
        dto.setSubtotal(convertToStringMoneyInIndex(getSubTotalFromCartItems(c.getCartItems())));
        dto.setTotal(convertToStringMoneyInIndex(getSubTotalFromCartItems(c.getCartItems())));

        return dto;
    }

    public String convertToStringMoneyInIndex(BigDecimal subTotalFromCartItems) {
        return "$"+String.valueOf(subTotalFromCartItems.setScale(2, RoundingMode.HALF_UP));
    }

    public BigDecimal getSubTotalFromCartItems(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.valueOf(0);

        for (CartItem i : cartItems) {
            BigDecimal price = i.getProductsAndSizes().getPrice();
            int quantities = i.getQuantityPurchase();
            BigDecimal result = price.multiply(BigDecimal.valueOf(quantities));

            total = total.add(result) ;
        }

        return total;
    }

    private void convertCartItemToCartItemDTO(CartDTO dto, Cart c) {
        for (CartItem i : c.getCartItems()) {
            BigDecimal price = i.getProductsAndSizes().getPrice();
            price = price.multiply(BigDecimal.valueOf(i.getQuantityPurchase()));
            String c_price = String.valueOf(price);

            CartItemDTO itemDTO = new CartItemDTO(
                    i.getProductsAndSizes().getProduct().getId(),
                    i.getProductsAndSizes().getProduct().getName(),
                    i.getQuantityPurchase(),
                    i.getProductsAndSizes().getProduct().getImages().get(0).getUrl(),
                    String.valueOf(i.getProductsAndSizes().getPrice()),
                    i.getProductsAndSizes().getSize().getName(),
                    c_price
                    );
//            if (i.getProductsAndSizes().getProduct().isDiscountable()) {
//                String p = CurrencyTool.getDiscountPrice(i.getProductsAndSizes().getPrice(),i.getProductsAndSizes().getProduct().getDiscount());
//                itemDTO.setPrice(p);
//            } else  {
//                itemDTO.setPrice(CurrencyTool.getPrice(i.getProductsAndSizes().getPrice()));
//            }



            dto.getCarts().add(itemDTO);
        }
    }

    public Cart getCartFromUser(String identity) {
        if(identity != null) {
            UserTrueMart user = userService.getUserByEmail(identity);

            return user.getCart();
        }

        return new Cart();
    }

    public Cart getCartFromUser(UserTrueMart user) {
        if(user != null) {
            return user.getCart();
        }

        return  new Cart();
    }

    public Cart saveCart(Cart c) {
        return  cartRepository.save(c);
    }
}
