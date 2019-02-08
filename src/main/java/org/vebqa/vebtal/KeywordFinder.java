package org.vebqa.vebtal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.annotations.Keyword;

public class KeywordFinder {

	private static final Logger logger = LoggerFactory.getLogger(KeywordFinder.class);

	private static final KeywordFinder finder = new KeywordFinder();
	
	private static final String cmdPackage = "org.vebqa.vebtal";
	
	private List<KeywordEntry> allCustomKeywords = new ArrayList<KeywordEntry>();
	
	public KeywordFinder() {
		logger.debug("Keywordfinder created, classpath not scanned yet. Use scan before usage.");
	}

	public static KeywordFinder getinstance() {
		return finder;
	}
	
	/**
	 * Scan will clear the keyword storage and make a fresh scan.
	 */
	public void scan() {
		allCustomKeywords.clear();
		
		Reflections reflection = new Reflections(KeywordFinder.cmdPackage);
		Set<Class<?>> clKeywords = reflection.getTypesAnnotatedWith(org.vebqa.vebtal.annotations.Keyword.class);
		
		for (Class<?> aKeywordClass : clKeywords) {
			Keyword annotation = aKeywordClass.getAnnotation(org.vebqa.vebtal.annotations.Keyword.class);
			if (annotation != null) {
				KeywordEntry newEntry = new KeywordEntry(annotation.module(), annotation.command(), annotation.hintTarget(), annotation.hintValue());
				allCustomKeywords.add(newEntry);
			}
		}
		
		logger.info(allCustomKeywords.size() + " custom keywords found and added to the storage.");
	}
	
	/**
	 * Returns all custom keywords, found by the annotation scanner. Needs a scan before.
	 * 
	 * @return list of all keywords defined
	 */
	public List<KeywordEntry> getKeywords() {
		return this.allCustomKeywords;
	}
	
	/**
	 * Returns all custom keywords, filtered by module name. Needs a scan before.
	 * 
	 * @param aModule a module to search for
	 * @return List of keywords
	 */
	public List<KeywordEntry> getKeywordsByModule(String aModule) {
		List<KeywordEntry> moduleKeywords = new ArrayList<KeywordEntry>();
		for (KeywordEntry aKeyword : this.allCustomKeywords) {
			if (aKeyword.getModule().contentEquals(aModule)) {
				moduleKeywords.add(aKeyword);
			}
		}
		logger.info(moduleKeywords.size() + " custom keywords for specific module found out of all: " + this.allCustomKeywords.size());
		return moduleKeywords;
	}
	
}
