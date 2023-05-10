package project2.dto;

import project2.model.*;

import java.util.List;

import project2.model.Cart;
import project2.model.Member;
import project2.model.PaymentMethod;
import project2.model.Transport;

public class PaymentDTO {
    private Long idPayment;
    private String firstNameReceiver;
    private String lastNameReceiver;
    private String city;
    private String district;
    private String ward;
    private String addressReceiver;
    private String emailReceiver;
    private String phoneReceiver;
    private Double feeService;
    private String descriptionReceiver;
    private Member member;
    private PaymentMethod paymentMethod;
    private Cart cart;
    private Transport transport;
    private Double total;
    private List<Product> product;

    public PaymentDTO() {
    }

    public PaymentDTO(Long idPayment, String firstNameReceiver, String lastNameReceiver, String city, String district, String ward, String addressReceiver, String emailReceiver, String phoneReceiver, Double feeService, String descriptionReceiver, Member member, PaymentMethod paymentMethod, Cart cart, Transport transport, Double total, List<Product> product) {
        this.idPayment = idPayment;
        this.firstNameReceiver = firstNameReceiver;
        this.lastNameReceiver = lastNameReceiver;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.addressReceiver = addressReceiver;
        this.emailReceiver = emailReceiver;
        this.phoneReceiver = phoneReceiver;
        this.feeService = feeService;
        this.descriptionReceiver = descriptionReceiver;
        this.member = member;
        this.paymentMethod = paymentMethod;
        this.cart = cart;
        this.transport = transport;
        this.total = total;
        this.product = product;
    }

    public Long getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(Long idPayment) {
        this.idPayment = idPayment;
    }

    public String getFirstNameReceiver() {
        return firstNameReceiver;
    }

    public void setFirstNameReceiver(String firstNameReceiver) {
        this.firstNameReceiver = firstNameReceiver;
    }

    public String getLastNameReceiver() {
        return lastNameReceiver;
    }

    public void setLastNameReceiver(String lastNameReceiver) {
        this.lastNameReceiver = lastNameReceiver;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddressReceiver() {
        return addressReceiver;
    }

    public void setAddressReceiver(String addressReceiver) {
        this.addressReceiver = addressReceiver;
    }

    public String getEmailReceiver() {
        return emailReceiver;
    }

    public void setEmailReceiver(String emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    public String getPhoneReceiver() {
        return phoneReceiver;
    }

    public void setPhoneReceiver(String phoneReceiver) {
        this.phoneReceiver = phoneReceiver;
    }

    public Double getFeeService() {
        return feeService;
    }

    public void setFeeService(Double feeService) {
        this.feeService = feeService;
    }

    public String getDescriptionReceiver() {
        return descriptionReceiver;
    }

    public void setDescriptionReceiver(String descriptionReceiver) {
        this.descriptionReceiver = descriptionReceiver;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
