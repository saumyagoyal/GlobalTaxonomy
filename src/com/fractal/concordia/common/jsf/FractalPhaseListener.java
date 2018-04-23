package com.fractal.concordia.common.jsf;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

public class FractalPhaseListener implements PhaseListener {

	private static final long serialVersionUID = -3409805086068596163L;
	private static final Logger log = Logger.getLogger(FractalPhaseListener.class.getName());

	@Override
	public void afterPhase(PhaseEvent event) {
		log.debug("afterPhase :: " + event.getPhaseId());
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		log.debug("beforePhase :: " + event.getPhaseId());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
