package com.unitedvision.tvkabel.util;

public class RestMessage {
	private String message;
	private Type tipe;
	private Object object;
	
	protected RestMessage(String message, Type tipe) {
		super();
		this.message = message;
		this.object = message;
		this.tipe = tipe;
	}
	
	protected RestMessage(String message, Type tipe, Object object) {
		this(message, tipe);
		this.object = object;
	}
	
	protected RestMessage(Exception ex) {
		this(ex.getMessage(), Type.ERROR);
	}
	
	private RestMessage(Object object) {
		this("Berhasil", Type.OBJECT);
		this.object = object;
		
		if (object instanceof String) {
			this.message = String.valueOf(object);
			this.tipe = Type.MESSAGE;
		}
	}

	public String getMessage() {
		return message;
	}
	
	public Type getTipe() {
		return tipe;
	}
	
	public Object getObject() {
		return object;
	}
	
	public enum Type {
		SUCCESS,
		ERROR,
		MESSAGE,
		ENTITY,
		LIST,
		OBJECT
	}

	public static RestMessage success() {
		return new RestMessage("Berhasil", Type.SUCCESS);
	}
	
	public static RestMessage error(Exception cause) {
		return new RestMessage(cause);
	}

	public static RestMessage create(Object object) {
		return new RestMessage(object);
	}
	
}
