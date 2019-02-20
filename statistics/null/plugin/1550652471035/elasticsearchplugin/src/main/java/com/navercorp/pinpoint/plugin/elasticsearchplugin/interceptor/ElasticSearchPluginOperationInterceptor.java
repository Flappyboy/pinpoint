/*
 * Copyright 2015 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.plugin.elasticsearchplugin.interceptor;

import com.navercorp.pinpoint.plugin.elasticsearchplugin.ElasticSearchPluginConstants;
import com.navercorp.pinpoint.bootstrap.context.MethodDescriptor;
import com.navercorp.pinpoint.bootstrap.context.SpanEventRecorder;
import com.navercorp.pinpoint.bootstrap.context.SpanRecorder;
import com.navercorp.pinpoint.bootstrap.context.TraceContext;
import com.navercorp.pinpoint.common.trace.AnnotationKey;

/**
 * @author yinbp
 */
public class ElasticSearchPluginOperationInterceptor extends ElasticSearchPluginBaseOperationInterceptor {
    private boolean recordResult = false;
    private boolean recordArgs = false;
    public ElasticSearchPluginOperationInterceptor(TraceContext context, MethodDescriptor descriptor) {
        super(context, descriptor);
        recordResult = this.getTraceContext().getProfilerConfig().readBoolean("profiler.elasticsearchplugin.recordResult",false);
        recordArgs = this.getTraceContext().getProfilerConfig().readBoolean("profiler.elasticsearchplugin.recordArgs",true);
    }

    @Override
    protected void doInBeforeTrace(SpanRecorder recorder, Object target, Object[] args, boolean newTrace) {
        if(newTrace) {
            StringBuilder buffer = new StringBuilder(256);
            buffer.append(methodDescriptor.getClassName());
            buffer.append(".");
            buffer.append(methodDescriptor.getMethodName());
            mergeParameterVariableNameDescription(buffer,methodDescriptor.getParameterTypes(),methodDescriptor.getParameterVariableName());
            String rpc = buffer.toString();//builder.append();
            recorder.recordRpcName(rpc);
            recorder.recordEndPoint(rpc);
            //recorder.recordRemoteAddress(rpc);
        }
    }

    @Override
    protected void doInBeforeTrace(SpanEventRecorder recorder, Object target, Object[] args,boolean newTrace) {
    }

    @Override
    protected void doInAfterTrace(SpanEventRecorder recorder, Object target, Object[] args, Object result,
            Throwable throwable,boolean newTrace) {
        recorder.recordServiceType(ElasticSearchPluginConstants.ElasticSearchPlugin_EVENT);
        recorder.recordException(throwable);
        if (recordArgs && args != null && args.length > 0) {
            recorder.recordApi(getMethodDescriptor());
            recorder.recordAttribute(ElasticSearchPluginConstants.ARGS_ANNOTATION_KEY,convertParams(args));
        } else {
            recorder.recordApi(getMethodDescriptor());
        }

        if(recordResult){
        	recorder.recordAttribute(AnnotationKey.RETURN_DATA,result);
		}
    }

    @Override
    protected void doInAfterTrace(SpanRecorder recorder, Object target, Object[] args, Object result, Throwable throwable) {
        recorder.recordServiceType(ElasticSearchPluginConstants.ElasticSearchPlugin);
        recorder.recordException(throwable);
        if (recordArgs && args != null && args.length > 0) {
            recorder.recordApi(getMethodDescriptor());
            recorder.recordAttribute(ElasticSearchPluginConstants.ARGS_ANNOTATION_KEY,convertParams(args));

        } else {
            recorder.recordApi(getMethodDescriptor());
        }

        if(recordResult){
            recorder.recordAttribute(AnnotationKey.RETURN_DATA,result);
        }
    }

}
