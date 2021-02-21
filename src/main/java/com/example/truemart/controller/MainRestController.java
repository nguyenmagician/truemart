package com.example.truemart.controller;

import com.example.truemart.entity.*;
import com.example.truemart.model.InCartModel;
import com.example.truemart.model.InCartResponse;
import com.example.truemart.service.*;
import com.example.truemart.testClass.Api;
import com.example.truemart.testClass.TestApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class MainRestController {
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final CompareService compareService;
    private final WishlistService wishlistService;


    public MainRestController(ProductService productService, UserService userService, CartService cartService, CompareService compareService, WishlistService wishlistService) {
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
        this.compareService = compareService;
        this.wishlistService = wishlistService;
    }

    @PostMapping("/incart")
    public ResponseEntity AddCartItemIntoCart( @Valid  @RequestBody InCartModel model) {
        Cart cart = cartService.getCartFromUser(model.getEmail());
        boolean flag = false;
        // If item already in cart, change quantities
        for (CartItem item : cart.getCartItems()) {
            if(item.getProductsAndSizes().getProduct().getId() == model.getId()) {
                flag = true;
                item.setQuantityPurchase(model.getQuantities());
                break;
            }
        }
        // If not, and new item become new cartitem, and we add new cartitem into cart
        if(!flag) {
            CartItem nItem = new CartItem();
            // new cartItem need productandsizes, quantities and also cart it's self properties
            Optional<ProductsAndSizes> pns;
            // get productandsizes whenever have only id,size or just id
            pns = !model.getSize().equals("") ? productService.SingleProductProvider(model.getId(),model.getSize()) : productService.SingleProductProvider(model.getId());

            if(pns.isPresent() ) {
                if(pns.get().isStocked()) {
                    nItem.setProductsAndSizes(pns.get());
                    nItem.setQuantityPurchase(model.getQuantities());
                    cart.addCartItem(nItem);

                    cartService.saveCart(cart);
                    return new ResponseEntity(new InCartResponse("ok"), HttpStatus.OK);
                } else {
                    return new ResponseEntity(new InCartResponse("Product is not available right now."), HttpStatus.BAD_REQUEST);
                }

            } else {
                return new ResponseEntity(new InCartResponse("Bad request"), HttpStatus.BAD_REQUEST);
            }


        }

        return new ResponseEntity(new InCartResponse("ok"), HttpStatus.OK);

    }

    @PostMapping("/inwishlist")
    public ResponseEntity AddWishlistItemIntoWishlist(@Valid @RequestBody InCartModel model) {
        // DONT READ DOCUMENT, IT'S DUPLICATED CART ABOVE , Reason : Lazy

        Wishlist cart = wishlistService.getWishlistFromUser(model.getEmail());
        if(cart== null) {
            return new ResponseEntity(new InCartResponse("no"), HttpStatus.BAD_REQUEST);
        }
        boolean flag = false;
        // If item already in cart, change quantities
        for (WishlistItem item : cart.getWishlistItems()) {
            if(item.getProductsAndSizes().getProduct().getId() == model.getId()) {
                flag = true;
                break;
            }
        }

        if(flag) {
            return new ResponseEntity(new InCartResponse("already"), HttpStatus.OK);
        }
        // If not, and new item become new cartitem, and we add new cartitem into cart
        if(!flag) {
            WishlistItem nItem = new WishlistItem();
            // new cartItem need productandsizes, quantities and also cart it's self properties
            Optional<ProductsAndSizes> pns = productService.SingleProductProvider(model.getId());
            // get productandsizes whenever have only id,size or just id


            if(pns.isPresent() ) {
                if(pns.get().isStocked()) {
                    nItem.setProductsAndSizes(pns.get());

                    cart.addWishlistItem(nItem);

                    wishlistService.save(cart);
                    return new ResponseEntity(new InCartResponse("ok"), HttpStatus.OK);
                } else {
                    return new ResponseEntity(new InCartResponse("Product is not available right now."), HttpStatus.BAD_REQUEST);
                }

            } else {
                return new ResponseEntity(new InCartResponse("Product is not exist"), HttpStatus.BAD_REQUEST);
            }


        }

        return new ResponseEntity(new InCartResponse("ok"), HttpStatus.OK);

    }

    @PostMapping("/incompare")
    public ResponseEntity AddCompareItemIntoCompare(@Valid @RequestBody InCartModel model) {
        // DONT READ DOCUMENT, IT'S DUPLICATED CART ABOVE , Reason : Lazy

        Compare cart = compareService.getCompareFromUser(model.getEmail());
        if(cart== null) {
            return new ResponseEntity(new InCartResponse("User doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        boolean flag = false;
        // If item already in cart, change quantities
        for (CompareItem item : cart.getCompareItems()) {
            if(item.getProductsAndSizes().getProduct().getId() == model.getId()) {
                flag = true;
                break;
            }
        }

        if(flag) {
            return new ResponseEntity(new InCartResponse("already"), HttpStatus.OK);
        }
        // If not, and new item become new cartitem, and we add new cartitem into cart
        if(!flag) {
            CompareItem nItem = new CompareItem();
            // new cartItem need productandsizes, quantities and also cart it's self properties
            Optional<ProductsAndSizes> pns = productService.SingleProductProvider(model.getId());
            // get productandsizes whenever have only id,size or just id


            if(pns.isPresent() ) {
                if(pns.get().isStocked()) {
                    nItem.setProductsAndSizes(pns.get());

                    cart.addCompareItem(nItem);

                    compareService.save(cart);
                    return new ResponseEntity(new InCartResponse("ok"), HttpStatus.OK);
                } else {
                    return new ResponseEntity(new InCartResponse("Product is not available right now."), HttpStatus.BAD_REQUEST);
                }

            } else {
                return new ResponseEntity(new InCartResponse("Product is not exist"), HttpStatus.BAD_REQUEST);
            }


        }

        return new ResponseEntity(new InCartResponse("ok"), HttpStatus.OK);

    }

    @GetMapping("/checkemail")
    public boolean ok(@RequestParam("email")String email){
        return userService.checkEmail(email);
    }

    @PostMapping(value = "/test_api",produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Api> test_api( @Valid @RequestBody TestApi test){


        Api api = new Api();
        api.setId(UUID.randomUUID());
        api.setEmail(test.getEmail());
        api.setName(test.getName());

        return new ResponseEntity(api,HttpStatus.OK);
    }
}
