package webscada.services.utils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import webscada.api.services.IProfilePrinter;

@Service
@Profile("!dev")
public class ProfileProd implements IProfilePrinter {

	@Override
	public String print() {
		return "Prod Profile is active";
	}
}
