package za.co.mmjmicrosystems.training.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class FlashUtils {
	
	private static MessageSource messageSource;
	
	@Autowired
	public FlashUtils(MessageSource messageSource) {
		FlashUtils.messageSource = messageSource;
	}
	
	public static void flash(RedirectAttributes redirectAttributes, String kind, String messageKey) {
		
		redirectAttributes.addFlashAttribute("flashKind", kind);
		redirectAttributes.addFlashAttribute("flashMessage", FlashUtils.getMessage(messageKey));
	}
	
	private static String getMessage(String messageKey, Object...args) {
		
		return messageSource.getMessage(messageKey, args, Locale.getDefault());
	}

}
