package org.vebqa.vebtal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.annotations.Keyword;

public class KeywordFinder {

	private static final Logger logger = LoggerFactory.getLogger(KeywordFinder.class);

	private static final KeywordFinder finder = new KeywordFinder();

	/**
	 * @deprecated
	 * package scope to resolve keywords
	 */
	private static final String cmdPackage = "org.vebqa.vebtal";

	/**
	 * common package scope (new)
	 */
	private static final String commonCommandPackage = "org.opental";

	/**
	 * TODO: Move list to central storage
	 * storage for all keywords found in classpath
	 */
	private List<KeywordEntry> allCustomKeywords = new ArrayList<KeywordEntry>();

	/**
	 * default
	 */
	public KeywordFinder() {
		logger.debug("Keywordfinder created, classpath not scanned yet. Use scan before usage.");
	}

	/**
	 * get the instance of keywordfinder
	 * 
	 * @return the instance
	 */
	public static KeywordFinder getinstance() {
		return finder;
	}

	/**
	 * Scan will clear the keyword storage and make a fresh scan.
	 */
	public void scan() {
		allCustomKeywords.clear();

		// @TODO: search only in command packages
		Reflections reflection = new Reflections(
				new ConfigurationBuilder().addUrls(ClasspathHelper.forPackage(KeywordFinder.cmdPackage))
						.addUrls(ClasspathHelper.forPackage(KeywordFinder.commonCommandPackage)));

		Set<Class<?>> clKeywords = reflection.getTypesAnnotatedWith(Keyword.class);

		for (Class<?> aKeywordClass : clKeywords) {
			Keyword annotation = aKeywordClass.getAnnotation(Keyword.class);
			if (annotation != null) {
				KeywordEntry newEntry = new KeywordEntry(annotation.module(), annotation.command(),
						annotation.hintTarget(), annotation.hintValue());
				allCustomKeywords.add(newEntry);
			}
		}

		logger.info(allCustomKeywords.size() + " custom keywords found and added to the storage.");
	}

	/**
	 * Returns all custom keywords, found by the annotation scanner. Needs a scan
	 * before.
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
		logger.info(moduleKeywords.size() + " custom keywords for specific module found out of all: "
				+ this.allCustomKeywords.size());
		return moduleKeywords;
	}

	public boolean isKeywordExisting(String aModule, String aCmd) {
		for (KeywordEntry aKeyword : this.allCustomKeywords) {
			if (aKeyword.getModule().contentEquals(aModule)) {
				if (aKeyword.getCommand().equalsIgnoreCase(aCmd)) {
					return true;
				}
			}
		}
		return false;
	}
}