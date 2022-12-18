package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.server.VaadinService;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.Cookie;

public class CookieReader {
    public CookieReader(){
    }

    public void createCookie(String name, String content){
        Cookie n = new Cookie(name, content);
        n.setMaxAge(-1);
        n.setPath("");
        VaadinService.getCurrentResponse().addCookie(n);
    }
    public String getCookie(String name){
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

        for (Cookie cookie : cookies) {
            String cname = cookie.getName();
            if (name.equals(cname)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
