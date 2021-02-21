package com.example.truemart.config;

import com.example.truemart.entity.*;
import com.example.truemart.repository.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.sql.Date;

@Component
public class AppConfig {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final RoleRepository roleRepository;

    private final BrandRepository brandRepository;

    private final ProductsAndSizesRepository pnsRepository;

    private final CategoriesAndBrandsAndProductsRepository cbpRepository;


    public AppConfig(ProductRepository productRepository, CategoryRepository categoryRepository, RoleRepository roleRepository, BrandRepository brandRepository, ProductsAndSizesRepository pnsRepository, CategoriesAndBrandsAndProductsRepository cbpRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
        this.brandRepository = brandRepository;
        this.pnsRepository = pnsRepository;
        this.cbpRepository = cbpRepository;
    }

    private void initRoles() {
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("USER"));
    }

    private void initCategories() {
        init4MainCategories();
        initChildsOf1MainCategories(DictionaryTrueMart.Category.get("Electronics"),Arrays.asList("Camera","Tablet","Laptop","MobilePhone"));
        initChildsOf1MainCategories(DictionaryTrueMart.Category.get("Fashions"),Arrays.asList("Women","Sport"));
        initChildsOf1MainCategories(DictionaryTrueMart.Category.get("Furnitures"),Arrays.asList("Living","Decor"));
        initChildsOf1MainCategories(DictionaryTrueMart.Category.get("Kids"),Arrays.asList("Toys","Baby"));
    }

    private void initBrands() {
        List<String> list = Arrays.asList("Canon","Nikon","Sony","Lenka","Samsung","LG","Xiaomi","Dell","Lenovo","Asus","Apple","Huawei","Google","Nike","Adidas","Muji","Uniqlo","Gucci","Chanel","Baccarat","CireTrvdon","IKEA","Lego");

        for(String s : list) {
            Brand b = new Brand(s);
            b = brandRepository.saveAndFlush(b);
            DictionaryTrueMart.Brand.put(s,b);
        }
    }

    private void initElectronicsProducts() {


        Product("Sony a7R","INCREDIBLE DETAIL: Shoot high speed subjects at up to 10fps with continuous, accurate AF/AE tracking\n" +
                "OPTIMAL LIGHT: A back illuminated Exmor R CMOS sensor with gapless on chip lens collects more light. Operating Temperature: 32 to 104 degrees Fahrenheit / 0 to 40 degrees Celsius",
                "High Resolution Interchangeable Lens Digital Camera with Front End LSI Image Processor",
                true,12,Arrays.asList("img\\products\\cam-4.jpg","img\\products\\cam-7.jpg"),
                DictionaryTrueMart.Brand.get("Sony"),DictionaryTrueMart.Category.get("Camera"),
                Arrays.asList(new SizeInform("DEFAULT",true,100, BigDecimal.valueOf(2999),true)
                ));
        Product("Nikon D3500","Auto, Landscape, Special Effects Modes (night vision; super vivid; pop; photo illustration; toy camera effect; miniature effect; selective color; silhouette; high key; low key), Night Portrait, Portrait, Auto [Flash Off], Close-up, Child, Sports",
                "A DSLR that's as easy to use as a point and shoot camera",
                true,12,Arrays.asList("img\\products\\cam-8.jpg","img\\products\\cam-6.jpg"),
                DictionaryTrueMart.Brand.get("Nikon"),DictionaryTrueMart.Category.get("Camera"),
                Arrays.asList(new SizeInform("DEFAULT",true,100, BigDecimal.valueOf(3999),true)
                ));
        Product("Ipad Pro 2020", "It’s a magical piece of glass. " +
                        "It’s so fast most PC laptops can’t catch up. " +
                        "It has pro cameras that can transform reality. " +
                        "And you can use it with touch, pencil, keyboard, and now trackpad. " +
                        "It’s the new iPad Pro. ",
                "Your next computer is not a computer.",
                true,10,Arrays.asList("img\\products\\21.jpg","img\\products\\22.jpg"),
                DictionaryTrueMart.Brand.get("Apple"),DictionaryTrueMart.Category.get("Tablet"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(2000),true)));

        Product("New iPad 10.2 Generation 2019", "This ipad keyboard case is designed for New iPad 8th 7th Generation 10.2 inch 2020 2019 release(Model Number:A2197/A2198/A2200), iPad Air 3 10.5\" 2019(Model Number:A2152/A2123/A2153) and iPad Pro 10.5 inch 2017 released (Model Number: A1701/A1709). Please be kindly check your iPad back model before purchasing ",
                "With this Stylish and Elegant design, it can transform your IPad into a laptop in less than a second.",
                true,10,Arrays.asList("img\\products\\tablet-2.jpg","img\\products\\tablet-1.jpg"),
                DictionaryTrueMart.Brand.get("Samsung"),DictionaryTrueMart.Category.get("Tablet"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

        Product("Apple MacBook Air 13.3\"", "with Retina Display, 1.1GHz Quad-Core Intel Core i5, 16GB Memory, 256GB SSD, Space Gray (Early 2020)",
                "Retina display with True Tone",
                true,10,Arrays.asList("img\\products\\laptop-1.jpg","img\\products\\laptop-2.jpg"),
                DictionaryTrueMart.Brand.get("Apple"),DictionaryTrueMart.Category.get("Laptop"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

        Product("Alienware M15 Gaming", "Alienware M15 Gaming Laptop, 9th Gen Intel Core i7-9750H, 15. 6\" FHD 1920x1080 144Hz IPS, 16GB DDR4, 2666MHz, 512GB SSD, NVIDIA GeForce GTX 1660 6GB GDDR6",
                "9th Generation Intel Core i7-9750h (6-Core, 12MB Cache, up to 4. 5GHz w/Turbo Boost)",
                true,10,Arrays.asList("img\\products\\laptop-1.jpg","img\\products\\laptop-2.jpg"),
                DictionaryTrueMart.Brand.get("Dell"),DictionaryTrueMart.Category.get("Laptop"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

        Product("IPhone 12", "This is an auto-renewed stored value card subscription. A stored value card is what Amazon uses to transmit your monthly service payment to Cricket Wireless. ",
                "Apple iPhone 12 [256GB, Blue] + Carrier Subscription [Cricket Wireless]",
                true,10,Arrays.asList("img\\products\\phone-1.jpg","img\\products\\phone-3.jpg"),
                DictionaryTrueMart.Brand.get("Apple"),DictionaryTrueMart.Category.get("MobilePhone"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

        Product("Galaxy Note 20 Ultra 5G", "Intelligent Battery & Super Fast Charge: Adaptive power that meets your needs with an all-day intelligent battery that learns from how you work and play to optimize battery life; Plus, Super Fast Charging boosts your battery in just minutes",
                "Samsung Electronics Galaxy Note 20 Ultra 5G Factory Unlocked Android Cell Phone, US Version, 128GB of Storage, Mobile Gaming Smartphone, Long-Lasting Battery, Mystic White",
                true,10,Arrays.asList("img\\products\\phone-2.jpg","img\\products\\phone-4.jpg"),
                DictionaryTrueMart.Brand.get("Samsung"),DictionaryTrueMart.Category.get("MobilePhone"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

        for (int i = 0; i < 15; i++) {
            Product("Apple MacBook Air 13.3\"", "with Retina Display, 1.1GHz Quad-Core Intel Core i5, 16GB Memory, 256GB SSD, Space Gray (Early 2020)",
                    "Retina display with True Tone",
                    true,10,Arrays.asList("img\\products\\laptop-1.jpg","img\\products\\laptop-2.jpg"),
                    DictionaryTrueMart.Brand.get("Apple"),DictionaryTrueMart.Category.get("Laptop"),
                    Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));
        }

    }

    private void initFashionsProducts() {
        Product("Women's Hooded Jacket", "popular aesthetic expression at a particular period and place and in a specific context, especially in clothing, footwear, lifestyle, accessories, makeup, hairstyle, and body proportions ",
                "Your warn clothes is here.",
                true,10,Arrays.asList("img\\products\\5.jpg","img\\products\\2.jpg"),
                DictionaryTrueMart.Brand.get("Gucci"),DictionaryTrueMart.Category.get("Women"),
                Arrays.asList(new SizeInform("S",true,1000, BigDecimal.valueOf(100),true),new SizeInform("M",true,2000, BigDecimal.valueOf(100),true)));

        Product("Beautiful Dresses Ladies ", "popular aesthetic expression at a particular period and place and in a specific context, especially in clothing, footwear, lifestyle, accessories, makeup, hairstyle, and body proportions ",
                " New Design Fashion women clothes  ladies Dress",
                true,10,Arrays.asList("img\\products\\women-1.jpg","img\\products\\women-1.jpg"),
                DictionaryTrueMart.Brand.get("Chanel"),DictionaryTrueMart.Category.get("Women"),
                Arrays.asList(new SizeInform("S",true,1000, BigDecimal.valueOf(100),true),new SizeInform("M",true,2000, BigDecimal.valueOf(100),true)));

        Product("Mens Sports T-Shirt", "We are a leading Manufacturer of mens sports t-shirt from Meerut, India",
                "We are a leading Manufacturer of mens sports t-shirt from Meerut, India.",
                true,10,Arrays.asList("img\\products\\sport-1.jpg","img\\products\\sport-2.jpg"),
                DictionaryTrueMart.Brand.get("Nike"),DictionaryTrueMart.Category.get("Sport"),
                Arrays.asList(new SizeInform("S",true,1000, BigDecimal.valueOf(100),true),new SizeInform("M",true,2000, BigDecimal.valueOf(100),true)));

        Product("Mens Sports T-Shirt", "We are a leading Manufacturer of mens sports t-shirt from Meerut, India",
                "We are a leading Manufacturer of mens sports t-shirt from Meerut, India.",
                true,10,Arrays.asList("img\\products\\sport-2.jpg","img\\products\\sport-1.jpg"),
                DictionaryTrueMart.Brand.get("Nike"),DictionaryTrueMart.Category.get("Sport"),
                Arrays.asList(new SizeInform("S",true,1000, BigDecimal.valueOf(100),true),new SizeInform("M",true,2000, BigDecimal.valueOf(100),true)));
        for (int i = 0; i < 15; i++) {
            Product("Women's Hooded Jacket", "popular aesthetic expression at a particular period and place and in a specific context, especially in clothing, footwear, lifestyle, accessories, makeup, hairstyle, and body proportions ",
                    "Your warn clothes is here.",
                    true,10,Arrays.asList("img\\products\\5.jpg","img\\products\\2.jpg"),
                    DictionaryTrueMart.Brand.get("Gucci"),DictionaryTrueMart.Category.get("Women"),
                    Arrays.asList(new SizeInform("S",true,1000, BigDecimal.valueOf(100),true),new SizeInform("M",true,2000, BigDecimal.valueOf(100),true)));

        }
    }

    private void initFurnituresProducts() {
//        Product("Chair", "This is a chair.This is a chair.",
//                "This is a chair.",
//                true,10,Arrays.asList("img\\products\\fur-11.jpg","img\\products\\fur-22.jpg"),
//                DictionaryTrueMart.Brand.get("IKEA"),DictionaryTrueMart.Category.get("Living"),
//                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));
//
//        Product("Chair", "This is a couch.This is a couch.",
//                "This is a couch.",
//                true,10,Arrays.asList("img\\products\\fur-33.jpg","img\\products\\fur-44.jpg"),
//                DictionaryTrueMart.Brand.get("IKEA"),DictionaryTrueMart.Category.get("Living"),
//                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

        for(int i = 0; i < 15; i++) {
            Product("Gold Mirrors", "Each of the three hanging mirrors has a small eyehole so they can be hung on a wall. The plastic frame is resilient and helps to protect the circle mirror so it is more durable",
                    "The gold mirrors for wall decor come in a pack of 3",
                    true,10,Arrays.asList("img\\products\\deco-1.jpg","img\\products\\deco-2.jpg"),
                    DictionaryTrueMart.Brand.get("IKEA"),DictionaryTrueMart.Category.get("Living"),
                    Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));
        }
        Product("Gold Mirrors", "Each of the three hanging mirrors has a small eyehole so they can be hung on a wall. The plastic frame is resilient and helps to protect the circle mirror so it is more durable",
                "The gold mirrors for wall decor come in a pack of 3",
                true,10,Arrays.asList("img\\products\\deco-1.jpg","img\\products\\deco-2.jpg"),
                DictionaryTrueMart.Brand.get("IKEA"),DictionaryTrueMart.Category.get("Decor"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

        Product("Decor Canvas Wall", "Bedroom Decor Canvas Wall Art Flower Pattern Prints Bathroom Abstract Pictures Modern Navy Framed Wall Decor Artwork for Walls Hang for Bedroom 4 Pieces Wall Decoration Size 14x14 Each Panel",
                "good idea for home interior walls decor such as living room",
                true,10,Arrays.asList("img\\products\\deco-3.jpg","img\\products\\deco-4.jpg"),
                DictionaryTrueMart.Brand.get("IKEA"),DictionaryTrueMart.Category.get("Decor"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));


    }

    private void initKidsProducts() {
        Product("Drum Toy for Kids", "Bloom,bloom,bloom....",
                "Bloom,bloom,bloom....",
                true,10,Arrays.asList("img\\products\\43.jpg","img\\products\\42.jpg"),
                DictionaryTrueMart.Brand.get("Muji"),DictionaryTrueMart.Category.get("Baby"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

        Product("LEGO Super House", "Rock LEGO Mario (figure not included) to sleep on the hammock, make him talk by placing him on his friend Yoshi’s tag and defeat the Goomba… this Expansion Set offers various fun ways to enhance players’ gameplay",
                "Kids can earn digital coins for their next level and enjoy lots of role-play fun when they add this cute House",
                true,10,Arrays.asList("img\\products\\43.jpg","img\\products\\42.jpg"),
                DictionaryTrueMart.Brand.get("Lego"),DictionaryTrueMart.Category.get("Toys"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

        for (int i = 0; i < 15; i++) {
            Product("LEGO Super House", "Rock LEGO Mario (figure not included) to sleep on the hammock, make him talk by placing him on his friend Yoshi’s tag and defeat the Goomba… this Expansion Set offers various fun ways to enhance players’ gameplay",
                    "Kids can earn digital coins for their next level and enjoy lots of role-play fun when they add this cute House",
                    true,10,Arrays.asList("img\\products\\43.jpg","img\\products\\42.jpg"),
                    DictionaryTrueMart.Brand.get("Lego"),DictionaryTrueMart.Category.get("Toys"),
                    Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));
        }

        Product("Toys", "This is a toy for kids.",
                "Toys",
                true,10,Arrays.asList("img\\products\\39.jpg","img\\products\\40.jpg"),
                DictionaryTrueMart.Brand.get("Muji"),DictionaryTrueMart.Category.get("Toys"),
                Arrays.asList(new SizeInform("DEFAULT",true,1000, BigDecimal.valueOf(999),true)));

    }


    @Transactional
    public void init() {
        initRoles();
        initCategories();
        initBrands();
        initElectronicsProducts();
        initFashionsProducts();
        initFurnituresProducts();
        initKidsProducts();
    }




    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void startUp() {
        List<Product> list = productRepository.findAll();

        if(list.size() == 0) {
            init();
        }
    }

    private void init4MainCategories() {
        List<String> list = Arrays.asList("Electronics","Fashions","Furnitures","Kids");

        for (String s : list) {
            Category c = new Category(s);
            c = categoryRepository.saveAndFlush(c);
            DictionaryTrueMart.Category.put(s,c);
            DictionaryTrueMart.ListChildrenOfCategory.put(s,new ArrayList<String>());
        }
    }

    private void initChildsOf1MainCategories(Category category, List<String> asList) {
        for (String s : asList) {
            Category c = new Category(s,category);
            c = categoryRepository.saveAndFlush(c);
            DictionaryTrueMart.Category.put(s,c);

            List<String> list = DictionaryTrueMart.ListChildrenOfCategory.get(category.getName());
            list.add(s);
        }
    }

    private void Product(String name,String long_detail,String short_detail,boolean isDiscountable,float discount,List<String> images,Brand brand, Category category,List<SizeInform> sizeInforms) {
        Product product = new Product(name,long_detail,short_detail,isDiscountable,discount);

        for(String img : images) {
            Image image = new Image(img);
            product.addImage(image);
        }

        product = productRepository.save(product);

        cbpRepository.save(new CategoriesAndBrandsAndProducts(category,brand,product));

        for (SizeInform s : sizeInforms) {
            ProductsAndSizes ps = ProductsAndSizes
                                .builder()
                                .size(new Size(s.size))
                                .product(product)
                                .isStocked(s.isStocked)
                                .stock(s.stock)
                                .isNew(s.isNew)
                                .price(s.price)
                                .updated(new Date(Calendar.getInstance().getTimeInMillis()))
                                .build();

            pnsRepository.save(ps);
        }

    }

}
