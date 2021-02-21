package com.example.truemart.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class UserTrueMart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String first_name;

    private String last_name;

    private String email;

    private String telephone;

    private String password;

    private boolean isSubscribe;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "userTrueMarts_and_roles",
joinColumns =@JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private List<Role> roles;

    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "compare_id")
    private Compare compare;


    public UserTrueMart() {
        this.roles = new ArrayList<>();
        this.cart = new Cart();
        this.wishlist = new Wishlist();
        this.compare = new Compare();
    }

    public void addRole(Role role) {
        role.getUsers().add(this);
        this.getRoles().add(role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSubscribe() {
        return isSubscribe;
    }

    public void setSubscribe(boolean subscribe) {
        isSubscribe = subscribe;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Compare getCompare() {
        return compare;
    }

    public void setCompare(Compare compare) {
        this.compare = compare;
    }
}
