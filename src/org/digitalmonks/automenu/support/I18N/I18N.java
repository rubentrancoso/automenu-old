/*
 * Created on Oct 29, 2007
 */
package org.digitalmonks.automenu.support.I18N;

import java.util.Locale;

/**
 * @author pieter
 */
public class I18N extends JarProperties {

	private static final long serialVersionUID = 2834699877504823192L;

	private static final String MESSAGE_FILE = "properties/messages";

	public I18N() {
        this(Locale.getDefault());
    }

    public I18N(Locale locale) {
        this(MESSAGE_FILE, locale);
    }

    public I18N(String messagesFilename, Locale locale) {
        super(messagesFilename + "_" + locale + ".properties", messagesFilename + ".properties");
    }

    public String getI18NMessage(String message) {
    	String i18NMessage = this.getString(message);
        if (i18NMessage != null)
        	message = i18NMessage;
        return message;
    }

}
