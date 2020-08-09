package web.model;

import org.springframework.security.core.GrantedAuthority;

// Этот класс реализует интерфейс GrantedAuthority, в котором необходимо переопределить только один метод getAuthority() (возвращает имя роли).
// Имя роли должно соответствовать шаблону: «ROLE_ИМЯ», например, ROLE_USER.
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }

}
