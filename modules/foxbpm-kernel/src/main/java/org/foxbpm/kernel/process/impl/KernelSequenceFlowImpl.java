/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 */
package org.foxbpm.kernel.process.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.foxbpm.kernel.behavior.KernelSequenceFlowBehavior;
import org.foxbpm.kernel.event.KernelEvent;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.InterpretableExecutionContext;

public class KernelSequenceFlowImpl extends KernelFlowElementImpl implements KernelSequenceFlow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected KernelFlowNodeImpl sourceRef;
	protected KernelFlowNodeImpl targetRef;
	protected List<KernelListener> kernelListeners;
	protected int orderId = 0;

	protected KernelSequenceFlowBehavior sequenceFlowBehavior;

	/** Graphical information: a list of waypoints: x1, y1, x2, y2, x3, y3, .. */
	protected List<Integer> waypoints = new ArrayList<Integer>();

	public KernelSequenceFlowImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
		processDefinition.getSequenceFlows().put(id, this);
	}

	public boolean isContinue(FlowNodeExecutionContext executionContext) {
		if (sequenceFlowBehavior == null) {
			return true;
		} else {
			return sequenceFlowBehavior.isContinue(executionContext);
		}
	}

	public void take(InterpretableExecutionContext executionContext) {
		executionContext.setFlowNode(null);
		executionContext.setSequenceFlow(this);
		// 执行令牌进入条线事件
		executionContext.fireEvent(KernelEvent.SEQUENCEFLOW_TAKE);
		executionContext.setSequenceFlow(null);

		executionContext.enter(getTargetRef());
	}

	public KernelFlowNodeImpl getSourceRef() {
		return sourceRef;
	}

	public void setTargetRef(KernelFlowNodeImpl targetRef) {
		this.targetRef = targetRef;
		targetRef.getIncomingSequenceFlows().add(this);
	}

	public void addKernelListener(KernelListener kernelListener) {
		if (kernelListeners == null) {
			kernelListeners = new ArrayList<KernelListener>();
		}
		kernelListeners.add(kernelListener);
	}

	// getters and setters
	// //////////////////////////////////////////////////////

	protected void setSourceRef(KernelFlowNodeImpl sourceRef) {
		this.sourceRef = sourceRef;
	}
	@SuppressWarnings("unchecked")
	public List<KernelListener> getKernelListeners() {
		if (kernelListeners == null) {
			return Collections.EMPTY_LIST;
		}
		return kernelListeners;
	}

	public KernelFlowNodeImpl getTargetRef() {
		return targetRef;
	}

	public void setKernelListeners(List<KernelListener> kernelListeners) {
		this.kernelListeners = kernelListeners;
	}

	public List<Integer> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Integer> waypoints) {
		this.waypoints = waypoints;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public KernelSequenceFlowBehavior getSequenceFlowBehavior() {
		return sequenceFlowBehavior;
	}

	public void setSequenceFlowBehavior(KernelSequenceFlowBehavior sequenceFlowBehavior) {
		this.sequenceFlowBehavior = sequenceFlowBehavior;
	}

	public String toString() {
		return new StringBuffer("(").append(sourceRef.getId()).append(")--")
				.append(id != null ? id + "-->(" : ">(").append(targetRef.getId()).append(")")
				.toString();
	}
}
