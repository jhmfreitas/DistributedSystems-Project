package com.forkexec.hub.domain.exceptions;

/** Exception used to signal that there there was an empty cart problem. */
public class PointsNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public PointsNotFoundException() {
	}

	public PointsNotFoundException(String message) {
		super(message);
	}
}