package webscada.services.utils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import webscada.api.services.IProfilePrinter;

@Service
@Profile("dev")
public class ProfileDev implements IProfilePrinter {

	@Override
	public String print() {
		return "Dev Profile is active";
	}
}
