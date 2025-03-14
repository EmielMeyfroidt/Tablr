package main;

/**
 * The TablrManagerListener interface defines contentsChanged() that is invoked by the observer.
 */
public interface TablrManagerListener {
	/**
	 * Invoked to notify that the contents managed by TablrManager have been modified.
	 */
	public void contentsChanged();
}
