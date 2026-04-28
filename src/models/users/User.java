package models.users;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String username;
    protected String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Авторизация
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println(username + " logged out");
    }

    //  Профиль (каждый наследник реализует сам)
    public abstract void    viewProfile();

    //  Геттеры
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    // пароль наружу НЕ даём
    public void setPassword(String password) {
        this.password = password;
    }

    // equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User: " + username + " (id=" + id + ")";
    }
}