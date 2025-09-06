package br.com.erudio.data.dto.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String fullName;
    private String password;

    public AccountCredentialDTO() {
    }

    public AccountCredentialDTO(String userName, String fullName, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccountCredentialDTO that = (AccountCredentialDTO) o;
        return Objects.equals(userName, that.userName) && Objects.equals(fullName, that.fullName) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, fullName, password);
    }

    @Override
    public String toString() {
        return "AccountCredentialDTO{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
