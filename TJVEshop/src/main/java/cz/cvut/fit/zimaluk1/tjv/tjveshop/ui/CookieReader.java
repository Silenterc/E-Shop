package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinService;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.Cookie;

public class CookieReader {
    public CookieReader(){
    }

    public void createCookie(String name, String content){
        Cookie n = new Cookie(name, content);
        n.setMaxAge(-1);
        n.setPath(VaadinService.getCurrentRequest() .getContextPath());
        VaadinService.getCurrentResponse().addCookie(n);
    }
    public String getCookie(String name){
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

        for (Cookie cookie : cookies) {
            String cname = cookie.getName();
            if (name.equals(cname) && cookie.getValue() != null) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * Deletes the cookie with name by setting its maxAge to 0
     * @param name
     */
    public void deleteCookie(String name){
        Cookie del = new Cookie(name, null);
        del.setMaxAge(0);
        del.setPath(VaadinService.getCurrentRequest() .getContextPath());
        VaadinService.getCurrentResponse().addCookie(del);
    }
}
