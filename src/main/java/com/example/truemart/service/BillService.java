package com.example.truemart.service;

import com.example.truemart.DTO.BillDTO;
import com.example.truemart.DTO.BillItemDTO;
import com.example.truemart.entity.*;
import com.example.truemart.model.BillTransactionException;
import com.example.truemart.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.Calendar;

@Service
public class BillService {
    private final CartService cartService;
    private final BillRepository billRepository;
    private final ProductService productService;
    private final UserService userService;


    public BillService(CartService cartService, BillRepository billRepository, ProductService productService, UserService userService) {
        this.cartService = cartService;
        this.billRepository = billRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public BillDTO getBillDTO(String identity) {
        UserTrueMart user = userService.getUserByEmail(identity);

        if(user != null) {
            Cart cart = cartService.getCartFromUser(user);

            BillDTO dto = new BillDTO();
            dto.setUser_email(identity);
            dto.setShipping_price("$0.00");
            dto.setTaxes("$0.00");
            dto.setPayment("Indirect Payment");
            dto.setSubtotal(cartService.convertToStringMoneyInIndex(cartService.getSubTotalFromCartItems(cart.getCartItems())));
            dto.setOrdertotal(cartService.convertToStringMoneyInIndex(cartService.getSubTotalFromCartItems(cart.getCartItems())));
            dto.setFirstName(user.getFirst_name());
            dto.setLastName(user.getLast_name());
            dto.setEmail_notification(user.getEmail());
            dto.setPhone(user.getTelephone());


            for(CartItem item : cart.getCartItems()) {
                BillItemDTO b= new BillItemDTO();
                b.setPnsid(item.getProductsAndSizes().getId());
                b.setQuantities(item.getQuantityPurchase());
                b.setProduct_name(item.getProductsAndSizes().getProduct().getName());
                b.setAmount(item.getProductsAndSizes().getStock());
                BigDecimal price = item.getProductsAndSizes().getPrice();
                price = price.multiply(BigDecimal.valueOf(item.getQuantityPurchase()));
                b.setPrice("$"+String.valueOf(price.setScale(2, RoundingMode.HALF_UP)));

                dto.getItems().add(b);
            }

            return dto;
        }

        return new BillDTO();
    }





    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = BillTransactionException.class)
    public long addBill(BillDTO billdto) throws BillTransactionException {
        // save Bill
        Bill bill = new Bill();
        for(BillItemDTO itemDTO: billdto.getItems() ) {
            ProductsAndSizes pns = checkAndSubtractStock(itemDTO.getPnsid(),itemDTO.getQuantities());
            BillItem billItem = new BillItem();
            billItem.setPns(pns);
            billItem.setPrice(pns.getPrice());
            billItem.setQuantities(itemDTO.getQuantities());

            bill.addBillDetail(billItem);
        }
        // --set blahblahblah
        UserTrueMart user = userService.getUserByEmail(billdto.getUser_email());
        bill.setUser(user);
        bill.setCreated(new Date(Calendar.getInstance().getTimeInMillis()));

        Shipping shipping = new Shipping();
        shipping.setCountry(billdto.getCountry());
        shipping.setFirstName(billdto.getFirstName());
        shipping.setLastName(billdto.getLastName());
        shipping.setCompanyName(billdto.getCompanyName());
        shipping.setAddress(billdto.getAddress());
        shipping.setAddress_option(billdto.getAddressOption());
        shipping.setCity(billdto.getTown_city());
        shipping.setEmail(billdto.getEmail_notification());
        shipping.setPhone(billdto.getPhone());

        bill.setShipping(shipping);
        bill.setNotes(billdto.getNotes());
        bill.setCoupon(billdto.getCoupon());

        bill.setTaxes(BigDecimal.ZERO);
        bill.setShippingPrice(BigDecimal.ZERO);
        bill.setTotalPrice(handlePriceTextToBigDecimal(billdto.getOrdertotal()));
        bill.setSubPrice(handlePriceTextToBigDecimal(billdto.getSubtotal()));


        // chua binding chinh xac trong checkout template
        bill = billRepository.save(bill);
        // if success, add brand new cart into current user where fuction call MakeBill
        user.setCart(new Cart());
        userService.saveUser(user);

        return bill.getId();
    }

    public BigDecimal handlePriceTextToBigDecimal(String text) {
        String price = text.substring(1,text.length()-3);
        return BigDecimal.valueOf(Long.parseLong(price));
    }

    @Transactional(propagation = Propagation.MANDATORY )
    public ProductsAndSizes checkAndSubtractStock(long pnsid, int quantities) throws BillTransactionException{
        ProductsAndSizes pns = productService.findProductsAndSizesById(pnsid);
        if(pns == null) {
            throw  new BillTransactionException("this product doesn't exist");
        }

        if(pns.getStock() == 0) {
            throw  new BillTransactionException(pns.getProduct().getName() + " is out stock right now. This product will come back soon. Please remove it from your cart if u want checkout. ");
        }

        if(pns.getStock() - quantities < 0) {
            throw new BillTransactionException("Your order of " +pns.getProduct().getName() +" ( " +quantities+" )" +  " is over current stock ( "+ pns.getStock() + " ) in warehouse. Please reduce!!");
        }

        pns.setStock(pns.getStock() - quantities);

        return pns;
    }


}
