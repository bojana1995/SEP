package udd_upp.anotacije;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import udd_upp.model.Korisnik;
import udd_upp.model.Permisija;
import udd_upp.service.KorisnikService;
import udd_upp.service.RolaService;

public class PermisijaInterceptor implements HandlerInterceptor {

	@Autowired
	private RolaService rolaService;

	@Autowired
	private KorisnikService korisnikService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Korisnik prijavljen = korisnikService.getCurrentUser();

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler; 
			Method method = handlerMethod.getMethod();
			String permissionName = null;
			if(method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
				if(method.isAnnotationPresent(PermisijaAnotacija.class)) {
					PermisijaAnotacija annotation = method.getAnnotation(PermisijaAnotacija.class);
					permissionName = annotation.naziv();
				} else {
					return true;
				}
			} else {
				return true;
			}

			if(prijavljen == null) {
				return false;
			}

			for(Permisija premisija : prijavljen.getRola().getPermisije()){
				if(premisija.getNaziv().equals(permissionName)){
					return true;
				}
			}

			return false;
		} else {
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
	
}
