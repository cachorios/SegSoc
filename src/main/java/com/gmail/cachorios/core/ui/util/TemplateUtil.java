package com.gmail.cachorios.core.ui.util;

public class TemplateUtil {

	public static String generateLocation(String basePage, String entityId) {
		return basePage + (entityId == null || entityId.isEmpty() ? "" : "/" + entityId);
	}
}
