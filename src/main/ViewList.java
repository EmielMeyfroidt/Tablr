package main;

public interface ViewList {
	public void openView(AbstractView view);

	public void closeView(AbstractView view);

	public void substituteView(AbstractView oldView, AbstractView newView);
}
