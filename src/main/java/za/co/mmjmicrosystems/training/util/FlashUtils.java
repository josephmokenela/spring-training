package za.co.mmjmicrosystems.training.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import za.co.mmjmicrosystems.training.dto.UserDetailsImpl;
import za.co.mmjmicrosystems.training.entities.User;
import za.co.mmjmicrosystems.training.services.impl.UserServiceImpl;

@Component
public class FlashUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(FlashUtils.class);
	
	private static MessageSource messageSource;
	
	private static String hostAndPort;
	
	private static String activeProfiles;
	

	@Autowired
	public FlashUtils(MessageSource messageSource) {
		FlashUtils.messageSource = messageSource;
	}
	
	@Value("${hostAndPort}")
	public void setHostAndPort(String hostAndPort) {
		FlashUtils.hostAndPort = hostAndPort;
	}
	
	@Value("${spring.profiles.active}")
	public void setActiveProfiles(String activeProfiles) {
		FlashUtils.activeProfiles = activeProfiles;
	}

	public static String hostUrl() {
		
		return ( isDev()? "http://" : "https://") + hostAndPort;
	}
	
	public static boolean isDev() {
		logger.info("Printing the active profiles " + activeProfiles);
		logger.info("Host and port  " + hostAndPort);
		return activeProfiles.equals("dev");
	}
	public static void flash(RedirectAttributes redirectAttributes, String kind, String messageKey) {
		
		redirectAttributes.addFlashAttribute("flashKind", kind);
		redirectAttributes.addFlashAttribute("flashMessage", FlashUtils.getMessage(messageKey));
	}
	
	public static String getMessage(String messageKey, Object...args) {
		
		return messageSource.getMessage(messageKey, args, Locale.getDefault());
	}

	public static User getSessionUser() {

		UserDetailsImpl auth = getAuth();
		if (auth != null)
			return auth.getUser();
		
		return null;
	}
	
	public static UserDetailsImpl getAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetailsImpl) {
				return (UserDetailsImpl) principal;
			}
		}
		
		return null;
	}

	public static void validate(boolean valid, String messageContent, Object...args) {
		if(!valid) {
			throw new RuntimeException(getMessage(messageContent, args));
		}
		
	}

}
