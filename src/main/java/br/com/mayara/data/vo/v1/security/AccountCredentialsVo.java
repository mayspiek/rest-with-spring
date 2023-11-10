package br.com.mayara.data.vo.v1.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public AccountCredentialsVo(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public boolean equals(Object o) { // equals and hashcode are used to compare objects
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCredentialsVo that = (AccountCredentialsVo) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
