package com.example.clip.enumeration;


public enum StatusPaymentEnum {
	NEW,
	PROCESSED;

	
	public static StatusPaymentEnum findByName( String name ) {
		for (StatusPaymentEnum one : StatusPaymentEnum.values()) {
			if (one.name().equals(name)) return one;
		}
		return null;
	}
}
