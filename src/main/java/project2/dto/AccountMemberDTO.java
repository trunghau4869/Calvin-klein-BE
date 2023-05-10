package project2.dto;

import project2.model.Account;
import project2.model.Rank;

import javax.validation.constraints.NotEmpty;

public class AccountMemberDTO {

    private String emailMember;
    /*private String addressMember;*/
    private String city;
    private String district;
    private String ward;
    private String phoneMember;
    private String idCardMember;
    private String paypalMember;
    private Boolean flagDelete;
    private String dateOfBirthMember;
    private String nameMember;
    private String username;
    private String password;
    private Boolean checkedClause;

    public Boolean getCheckedClause() {
        return checkedClause;
    }

    public void setCheckedClause(Boolean checkedClause) {
        this.checkedClause = checkedClause;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*  @NotEmpty(message = "Confirm password not empty")
    private String confirmPassword;*/

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

    public AccountMemberDTO() {
    }

    public String getNameMember() {
        return nameMember;
    }

    public void setNameMember(String nameMember) {
        this.nameMember = nameMember;
    }

    public String getDateOfBirthMember() {
        return dateOfBirthMember;
    }

    public void setDateOfBirthMember(String dateOfBirthMember) {
        this.dateOfBirthMember = dateOfBirthMember;
    }

    public String getEmailMember() {
        return emailMember;
    }

    public void setEmailMember(String emailMember) {
        this.emailMember = emailMember;
    }

  /*  public String getAddressMember() {
        return addressMember;
    }

    public void setAddressMember(String addressMember) {
        this.addressMember = addressMember;
    }*/

    public String getPhoneMember() {
        return phoneMember;
    }

    public void setPhoneMember(String phoneMember) {
        this.phoneMember = phoneMember;
    }

    public String getIdCardMember() {
        return idCardMember;
    }

    public void setIdCardMember(String idCardMember) {
        this.idCardMember = idCardMember;
    }

    public String getPaypalMember() {
        return paypalMember;
    }

    public void setPaypalMember(String paypalMember) {
        this.paypalMember = paypalMember;
    }

    public Boolean getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Boolean flagDelete) {
        this.flagDelete = flagDelete;
    }

    /*    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }*/
}
