package com.example.truemart.controller;

import com.example.truemart.DTO.*;
import com.example.truemart.entity.*;
import com.example.truemart.model.BillTransactionException;
import com.example.truemart.model.MaxMinPrice;
import com.example.truemart.model.SubCategory;
import com.example.truemart.repository.ProductsAndSizesRepository;
import com.example.truemart.service.*;
import com.example.truemart.tools.EncryptDecrypt;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping
public class MainController {
        //HttpSessionCollector.find(id);
        private final ProductsAndSizesRepository productsAndSizesRepository;

        private final CategoryService categoryService;

        private final ProductService productService;

        private final UserService userService;

        private final CartService cartService;

        private final BillService billService;

        private final WishlistService wishlistService;

        private final CompareService compareService;

        public MainController(ProductsAndSizesRepository productsAndSizesRepository, CategoryService categoryService, ProductService productService, UserService userService, CartService cartService, BillService billService, WishlistService wishlistService, CompareService compareService) {
            this.productsAndSizesRepository = productsAndSizesRepository;
            this.categoryService = categoryService;
            this.productService = productService;
            this.userService = userService;
            this.cartService = cartService;
            this.billService = billService;
            this.wishlistService = wishlistService;
            this.compareService = compareService;
        }

        public void xulyxyz(Model model,HttpServletRequest request) {
            if(SecurityContextHolder.getContext().getAuthentication() != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                System.out.println("trong xyz," + authentication.toString());
                // neu user da nang nhap (1)
                // neu user chua dang nhap (2)
                if(!(authentication instanceof AnonymousAuthenticationToken)) { // (1) da dang nhap
                    UserDetails user = (UserDetails) authentication.getPrincipal();

                    // them thuoc tinh isLogined vao model
                    model.addAttribute("isLogined",true);
                    model.addAttribute("username",userService.getFirstNameOfUserHaveEmail(user.getUsername()));
                    model.addAttribute("user_email",user.getUsername());
                    model.addAttribute("cart", cartService.getCartDTO(user.getUsername()));
                    // add cart trong user(db).cart vao session


                }else { // (2) chua dang nhap
                    // them thuoc tinh isLogined vao model
                    model.addAttribute("isLogined",false);
                    model.addAttribute("cart",new CartDTO());

                }
            } else {
                model.addAttribute("isLogined",false);
                model.addAttribute("cart",new CartDTO());
            }

            model.addAttribute("categories_search",categoryService.getAllCategoriesName() );
        }

        public void ham_luoi(Model m,List<SingleProduct> products,int maxPage,String url) {
            m.addAttribute("products",products);
            m.addAttribute("maxPage",maxPage);
            m.addAttribute("url",url);
        }

        @GetMapping("/")
        public String home(Model model, Principal principal, HttpServletRequest request) {
                xulyxyz(model,request);
                // "Electronics","Fashions","Furnitures","Kids"
                model.addAttribute("Electronics",productService.getStoreFront("Electronics"));
                model.addAttribute("Fashions",productService.getStoreFront("Fashions"));
                model.addAttribute("Furnitures",productService.getStoreFront("Furnitures"));
                model.addAttribute("Kids",productService.getStoreFront("Kids"));
                model.addAttribute("hotdeals",productService.getHotDeals());

                LocalDate localDate = LocalDate.now().plusDays(1);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String promotion = dtf.format(localDate);
                model.addAttribute("promotion",promotion);


                return "index";
        }

        @GetMapping("/error")
        public String error(Model model,HttpServletRequest request) {xulyxyz(model,request);return "error";}

        @RequestMapping(value = "/shop",method = {RequestMethod.GET,RequestMethod.POST})
        public String shop(@RequestParam(name = "category") List<String> category,
                           @RequestParam(name = "size",required = false) List<String> size,
                           @RequestParam(name="page",required = false,defaultValue = "0")String requestPage,
                           @RequestParam(name = "sizep",required = false,defaultValue = "12")String requestSize,
                           @RequestParam(name = "brand",required = false,defaultValue = "")List<String> brand_list,
                           @RequestParam(name = "sort",required = false,defaultValue = "default")String sort,
                           @RequestParam(name = "max",required = false,defaultValue = "0") int max,
                           @RequestParam(name = "min",required = false,defaultValue = "0") int min,
                           @RequestParam(name = "search",required = false,defaultValue = "")String search,
                           Model m, HttpServletRequest request) {
                xulyxyz(m,request);

                Pageable pageable;
                int page;
                int sizep;

                try {
                    page = Integer.parseInt(requestPage);
                    sizep = Integer.parseInt(requestSize);
                    if(page < 0 || sizep < 0 || sizep > 4000)
                        throw new NumberFormatException();
                    if(page != 0)
                        page = page - 1;
                } catch (NumberFormatException ex) {
                    return "error";
                }

                switch(sort) {
                    case "atoz":
                        pageable = PageRequest.of(page,sizep, Sort.by("product.name").ascending());
                        break;
                    case "ztoa":
                        pageable = PageRequest.of(page,sizep, Sort.by("product.name").descending());
                        break;
                    case "high":
                        pageable = PageRequest.of(page,sizep, Sort.by("price").descending());
                        break;
                    case "low":
                        pageable = PageRequest.of(page,sizep, Sort.by("price").ascending());
                        break;
                    default:
                        pageable = PageRequest.of(page,sizep);
                        break;
                }

                if(category.size() == 0){
                    return "error";
                }

                List<SingleProduct> products;
                String url = "";
                int maxPage;
                List<SubCategory> subCategories;
                List<String> sizes;
                long min_q;
                long max_q;
                int currentPage = page + 1;
                String category_q = category.get(0).toLowerCase();
                //String brand_q = brand;
                List<String> brands_return;

                subCategories = productService.getSubCategories(category);
                sizes = productService.findSizesBy(category);
                MaxMinPrice mami = productService.findMaxMinBy(category);
                min_q = mami.getMin().longValue();
                max_q =  mami.getMax().longValue();
                brands_return = productService.getBrandsFromCategory(category_q);

                m.addAttribute("subCategories",subCategories);
                m.addAttribute("sizes",sizes);
                m.addAttribute("min",min_q);
                m.addAttribute("max",max_q);
                m.addAttribute("currentPage",currentPage);
                m.addAttribute("category",category_q);
                m.addAttribute("brands",brands_return);

            if(!search.equals("")) {
                String n_search = search.trim();
                String[] arr_str = n_search.split(" ");
                List<String> words = Arrays.asList(arr_str);

                products = productService.getSingleProductsWithSearchFrom(category,words,pageable);
                maxPage = productService.getMaxPageOfSearch(category,words,pageable);
                url = "/shop?category="+category_q + "&search="+n_search ;
                url += "&page=";
                // them khung search vao trong cho shop

                ham_luoi(m,products,maxPage,url);
            } else if (brand_list.size() >= 1 ){
                if(max!= 0 && size.size() != 0) {
                    products = productService.getSingleProductsFrom(category,brand_list,size,BigDecimal.valueOf(min),BigDecimal.valueOf(max),pageable);
                    maxPage = productService.getMaxPageHavingBrand(category,brand_list,size,BigDecimal.valueOf(min),BigDecimal.valueOf(max),pageable);
                    url = "/shop?category="+category_q;

                    for(int i=0;i< brand_list.size();i++) {
                        url += "&brand"+ brand_list.get(i);
                    }

                    for(int i =0; i < size.size();i++) {
                        url += "&size=" + size.get(i);
                    }

                    url += "&min=" + min + "&max=" + max + "&page=";

                    ham_luoi(m,products,maxPage,url);
                }else {
                    products = productService.getSingleProductsFrom(category,brand_list,pageable);
                    maxPage = productService.getMaxPageHavingBrand(category,brand_list,pageable);

                    url = "/shop?category="+category_q;

                    for(int i=0;i< brand_list.size();i++) {
                        url += "&brand"+ brand_list.get(i);
                    }

                    url += "&page=";

                    ham_luoi(m,products,maxPage,url);
                }
            } else if (category.size() == 1 && !category_q.equals("") ) {
                    products = productService.getSingleProductsFrom(category,pageable);
                    maxPage = productService.getMaxPage(category,pageable);
                    url = "/shop?category="+category_q + "&page=";

                     ham_luoi(m,products,maxPage,url);
            } else {
                return "error";
            }



//                if (!search.equals("")) {
//
//                }
//                 else if(!brand.equals("")) {
//                        if(max != 0 && size.size() != 0) {
//                            products = productService.getSingleProductsFrom(category,brand,size,BigDecimal.valueOf(min),BigDecimal.valueOf(max),pageable);
//                            subCategories = productService.getSubCategories(category);
//                            sizes = productService.findSizesBy(category,brand);
//                            MaxMinPrice mami = productService.findMaxMinBy(category,brand,size);
//                            min_q = mami.getMin().longValue();
//                            max_q =  mami.getMax().longValue();
//                            maxPage = productService.getMaxPage(category,brand,size,BigDecimal.valueOf(min),BigDecimal.valueOf(max),pageable);
//
//                            url = "/shop?category="+category.get(0) + "&brand="+brand + "&min=" + min + "&max=" + max;
//                            for(int i =0; i < size.size();i++) {
//                                url += "&size=" + size.get(i);
//                            }
//                            url += "&page=";
//
//                            m.addAttribute("products",products);
//
//                            m.addAttribute("currentPage",currentPage);
//                            m.addAttribute("maxPage",maxPage);
//                            //m.addAttribute("brand",brand_q);
//                            m.addAttribute("url",url);
//
//                        }
//                        else {
//                            products = productService.getSingleProductsFrom(category,brand,pageable);
//                            subCategories = productService.getSubCategories(category);
//                            sizes = productService.findSizesBy(category,brand);
//                            MaxMinPrice mami = productService.findMaxMinBy(category,brand);
//                            min_q = mami.getMin().longValue();
//                            max_q =  mami.getMax().longValue();
//                            maxPage = productService.getMaxPage(category,brand,pageable);
//
//                            url = "/shop?category="+category.get(0) + "&brand="+brand;
//                            url += "&page=";
//
//                            m.addAttribute("products",products);
//                            m.addAttribute("subCategories",subCategories);
//                            m.addAttribute("sizes",sizes);
//                            m.addAttribute("min",min_q);
//                            m.addAttribute("max",max_q);
//                            m.addAttribute("currentPage",currentPage);
//                            m.addAttribute("maxPage",maxPage);
//                            //m.addAttribute("brand",brand_q);
//                            m.addAttribute("url",url);
//                            m.addAttribute("category",category_q);
//                        }
//
//
//                } else {
//                    if(max != 0 && size.size() != 0) {
//                        products = productService.getSingleProductsFrom(category,size,BigDecimal.valueOf(min),BigDecimal.valueOf(max),pageable);
//                        subCategories = productService.getSubCategories(category);
//                        sizes = productService.findSizesBy(category);
//                        MaxMinPrice mami = productService.findMaxMinBy(category,size);
//                        min_q = mami.getMin().longValue();
//                        max_q =  mami.getMax().longValue();
//                        maxPage = productService.getMaxPage(category,size,BigDecimal.valueOf(min),BigDecimal.valueOf(max),pageable);
//
//                        url = "/shop?category="+category.get(0) + "&min=" + min + "&max=" + max;
//                        for(int i =0; i < size.size();i++) {
//                            url += "&size=" + size.get(i);
//                        }
//                        url += "&page=";
//
//                        m.addAttribute("products",products);
//                        m.addAttribute("subCategories",subCategories);
//                        m.addAttribute("sizes",sizes);
//                        m.addAttribute("min",min_q);
//                        m.addAttribute("max",max_q);
//                        m.addAttribute("currentPage",currentPage);
//                        m.addAttribute("maxPage",maxPage);
//                        //m.addAttribute("brand",brand_q);
//                        m.addAttribute("url",url);
//                        m.addAttribute("category",category_q);
//                    }
//                    else {
//                        products = productService.getSingleProductsFrom(category,pageable);
//                        subCategories = productService.getSubCategories(category);
//                        sizes = productService.findSizesBy(category);
//                        MaxMinPrice mami = productService.findMaxMinBy(category);
//                        min_q = mami.getMin().longValue();
//                        max_q =  mami.getMax().longValue();
//                        maxPage = productService.getMaxPage(category,pageable);
//
//                        url = "/shop?category="+category.get(0) ;
//                        url += "&page=";
//
//                        m.addAttribute("products",products);
//                        m.addAttribute("subCategories",subCategories);
//                        m.addAttribute("sizes",sizes);
//                        m.addAttribute("min",min_q);
//                        m.addAttribute("max",max_q);
//                        m.addAttribute("currentPage",currentPage);
//                        m.addAttribute("maxPage",maxPage);
//                        //m.addAttribute("brand",brand_q);
//                        m.addAttribute("url",url);
//                        m.addAttribute("category",category_q);
//                    }
//
//
//                }




            return "shop";
        }

        @GetMapping("/product")
        public String product(Model model,HttpServletRequest request,
                              @RequestParam(name = "id",defaultValue = "")String id,
                              @RequestParam(name = "size",defaultValue = "",required = false)String size){
                xulyxyz(model, request);
                ProductDTO dto;
                List<String> sizes;
                if(id.equals("")) {
                    return "error";
                }

                long pid = Long.valueOf(id);

                if(size.equals("")) {
                    Optional<ProductsAndSizes> pns = productService.SingleProductProvider(pid);
                    if(pns.isEmpty()) {
                        return "error";
                    }
                    dto = productService.convertProductsAndSizesToProductDTO(pns.get());
                    sizes = productService.getSizesOfProduct(pid);

                }else {
                    Optional<ProductsAndSizes> pns = productService.SingleProductProvider(pid,size);
                    if(pns.isEmpty()) {
                        return "error";
                    }
                    dto = productService.convertProductsAndSizesToProductDTO(pns.get(),size.toUpperCase());
                    sizes = productService.getSizesOfProduct(pid);
                }

                model.addAttribute("product",dto);
                model.addAttribute("sizes",sizes);

            return "product";
        }

        @GetMapping("/cart")
        public String cart(Model model,HttpServletRequest request,@RequestParam(value = "error",required = false)String error){
                xulyxyz(model,request);
                if(error!=null || error!="") {
                    model.addAttribute("error",error);
                }

                return "cart";
        }

    @PostMapping("/cart")
    public String cartPost(@RequestParam(name = "email")String email,@ModelAttribute("cart")CartDTO dto){


            cartService.updateCartFromCartDTO(dto,email);



        return "redirect:/cart";
    }


    @GetMapping("/wishlist")
    public String wishlist(Model model,HttpServletRequest request) {
        xulyxyz(model,request);
        model.addAttribute("wishlist",wishlistService.getWistlistDTO((String) model.getAttribute("user_email")));

        return "wishlist";
    }

    @PostMapping("/wishlist")
    public String wishlistPost(@RequestParam(name = "email")String email,@ModelAttribute("wishlist")WishlistDTO dto){


        wishlistService.updateWishlishFromWishlistDTO(dto,email);


        return "redirect:/wishlist";
    }

    @GetMapping("/compare")
    public String compare(Model model,HttpServletRequest request) {
        xulyxyz(model,request);
        model.addAttribute("compare",compareService.getCompareDTO((String) model.getAttribute("user_email")));
        model.addAttribute("encrypt_email", EncryptDecrypt.encrypt((String) model.getAttribute("user_email")));

        return "compare";
    }

    @PostMapping("/compare")
    public String postCompare(Model model,HttpServletRequest request,@RequestParam("d")String delete_product_id,@RequestParam("e")String email) {
            String email_decrypt = EncryptDecrypt.decrypt(email);

            boolean result = compareService.removeItem(Long.valueOf(delete_product_id),email_decrypt);
            if(!result)
                    return "error";

            return "redirect:/compare";
    }


        @GetMapping("/register")
        public String register(Model model,HttpServletRequest request) {
                xulyxyz(model,request);
                model.addAttribute("register",new RegisterDTO());


                return "register";
        }

        @PostMapping("/registersubmit")
        public String registersubmit(@ModelAttribute("register")RegisterDTO registerDTO) {
                registerDTO.toString();
                userService.saveUserWithUserRole(registerDTO);

                return "redirect:/login?new=true";
        }



        @RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
        public String login(Model model,HttpServletRequest request,
                            @RequestParam(name = "new",required = false,defaultValue = "false")String newuser,
                            @RequestParam(name = "error",required = false,defaultValue = "false")String error
                            ) {


                String message = newuser.equals("true") ? "Chúc mừng bạn đăng ký thành công, mời đăng nhập" : "Welcome to TrueMart ";
                model.addAttribute("message",message);

                xulyxyz(model,request);
                boolean logined = (boolean)model.getAttribute("isLogined");
                if(logined) {
                    return "redirect:/";
                }

                return "login";
        }

        @GetMapping("/checkout")
        public String checkout(Model model,HttpServletRequest request) {
            xulyxyz(model,request);
            String email = (String) model.getAttribute("user_email");

            BillDTO dto = billService.getBillDTO(email);

            if(dto.getOrdertotal().equals("$0.00")) {
                return "redirect:/";
            }

            model.addAttribute("billdto",dto);

            List<String> countries = Arrays.asList("VietNam","China","Malaysia","Thailand","USA","England","Canada","Germany","Rusia","Argentina");
            model.addAttribute("countries",countries);

            List<String> payments = Arrays.asList("Indirect Payment","Momo","Paypal");
            model.addAttribute("payments",payments);

            return "checkout";
        }

        @PostMapping("/checkout")
        public String postCheckout(@ModelAttribute("billdto")BillDTO dto,Model model,HttpServletRequest request) {


            long bill_id = 0;

            try {

                bill_id = billService.addBill(dto);

            }catch (BillTransactionException ex) {


                return "redirect:/cart?error="+ ex.getMessage();
            }

            String url = "";
            if(bill_id != 0) {
                url = "Bạn đã đặt hàng thành công,mã hóa đơn của bạn là MSHD-TM00" + bill_id;
            }
            xulyxyz(model,request);
            model.addAttribute("bill_message",url);


            return "success";
        }





        @GetMapping("/single-blog")
        public String singleblog(Model model,HttpServletRequest request) {
            xulyxyz(model,request);return "single-blog";
        }

        @GetMapping("/about")
        public String about(Model model,HttpServletRequest request) {
            xulyxyz(model,request);
            return "about";
        }

        @GetMapping("/contact")
        public String contact(Model model,HttpServletRequest request) {
            xulyxyz(model,request);
            return "contact";
        }


        @GetMapping("/test")
        public String test(Model model,HttpServletRequest request){

             xulyxyz(model,request);
            try {
                List<String> search = Arrays.asList("Sony");
                Pageable pageable = pageable = PageRequest.of(0,12);


               Page<ProductsAndCategories> ps= productsAndSizesRepository.findPagesOfProductsAndSizesByCategoryParent(Arrays.asList("Electronics","Fashions"),Arrays.asList("$&","&^"),pageable);
               List<ProductsAndCategories> pas = productsAndSizesRepository.findProductsAndSizesWithSearchByCategoryParent(Arrays.asList("Electronics","Fashions"),Arrays.asList("$&","&^"),pageable);

               for(ProductsAndCategories p : pas) {
                   System.out.println(p.getProduct().getName());
               }

                System.out.println(ps.getTotalPages());


            } catch (Exception ex) {
                System.out.println("loi roi");
            }

            return "about";
        }

}
