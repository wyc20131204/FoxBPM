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
 * @author yangguangftlp
 */
package org.foxbpm.connector.test.flowconnector.RunGroovyExpression;

import static org.junit.Assert.assertNotNull;

import org.foxbpm.engine.datavariable.VariableInstance;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 脚本执行
 * 
 * @author yangguangftlp
 * @date 2014年7月15日
 */
public class RunGroovyExpressionTest extends AbstractFoxBpmTestCase {
	
	/**
	 * 测试进入节点时或离开时执行脚本
	 * <p>1.使用场景：节点进入或离开时执行脚本</p>
	 * <p>2.预置条件：<p>
	 *          1.在任务节点进入或离开时配置连接器
	 * <p>3.处理过程：首先，驱动流程进入节点或离开节点</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，相应查看结果</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/connector/test/flowconnector/RunGroovyExpression/RunGroovyExpression_1.bpmn" })
	public void testRunGroovyExpression() {
		Authentication.setAuthenticatedUserId("admin");
		// 启动流程
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("RunGroovyExpression_1");
		// 涉及到变量结果
		VariableInstance vi = this.runtimeService.createVariableQuery().processInstanceId(pi.getId()).addVariableKey("data").singleResult();
		Object dataValue = null;
		if (null != vi) {
			dataValue = vi.getValueObject();
		}
		assertNotNull(dataValue);
	}
}
