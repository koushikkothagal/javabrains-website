package io.javabrains.web.login;

import com.auth0.NonceUtils;
import com.auth0.SessionUtils;

import io.javabrains.config.AppConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AppConfig appConfig;

    @Autowired
    public LoginController(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    protected String login(final Map<String, Object> model, final HttpServletRequest req) {
        logger.debug("Performing login");
        detectError(model);
        // add Nonce to storage
        NonceUtils.addNonceToStorage(req);
        model.put("clientId", appConfig.getClientId());
        model.put("clientDomain", appConfig.getDomain());
        String requestURL = req.getRequestURL().toString();
        String loginCallback = requestURL.replaceAll(req.getRequestURI(), "") + appConfig.getLoginCallback();
        
        model.put("loginCallback", loginCallback);
        model.put("state", SessionUtils.getState(req));
        // for this sample only, this just allows configuration to
        // use Lock Widget or Auth0.js for login presentation
        // should only enable loginCustom for DB connection
        return "login";
    }

    private void detectError(final Map<String, Object> model) {
        if (model.get("error") != null) {
            model.put("error", true);
        } else {
            model.put("error", false);
        }
    }


}
