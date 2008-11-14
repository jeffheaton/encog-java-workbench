package org.encog.workbench;

/**
 * General error class for Encog.
 * @author jheaton
 */
public class WorkBenchError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5909341149180956178L;

	/**
	 * Construct a message exception.
	 * 
	 * @param msg
	 *            The exception message.
	 */
	public WorkBenchError(final String msg) {
		super(msg);
	}

	/**
	 * Construct an exception that holds another exception.
	 * 
	 * @param t
	 *            The other exception.
	 */
	public WorkBenchError(final Throwable t) {
		super(t);
	}	
	
}
