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

package com.navercorp.pinpoint.plugin.elasticsearchplugin;

import com.navercorp.pinpoint.bootstrap.instrument.*;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformCallback;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformTemplate;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformTemplateAware;
import com.navercorp.pinpoint.bootstrap.interceptor.scope.ExecutionPolicy;
import com.navercorp.pinpoint.bootstrap.logging.PLogger;
import com.navercorp.pinpoint.bootstrap.logging.PLoggerFactory;
import com.navercorp.pinpoint.bootstrap.plugin.ProfilerPlugin;
import com.navercorp.pinpoint.bootstrap.plugin.ProfilerPluginSetupContext;
import com.navercorp.pinpoint.common.trace.ServiceType;
import com.navercorp.pinpoint.common.trace.ServiceTypeFactory;

import java.security.ProtectionDomain;
import java.util.List;


import com.navercorp.pinpoint.common.trace.*;
/**
 * @author yinbp
 */
public class ElasticSearchPluginPlugin implements ProfilerPlugin, TransformTemplateAware {

    private final PLogger logger = PLoggerFactory.getLogger(this.getClass());

    private TransformTemplate transformTemplate;

    @Override
    public void setup(ProfilerPluginSetupContext context) {

        ElasticSearchPluginPluginConfig elasticsearchpluginPluginConfig = new ElasticSearchPluginPluginConfig(context.getConfig());
        if (logger.isInfoEnabled()) {
            logger.info("ElasticSearchPluginPlugin config:{}", elasticsearchpluginPluginConfig);
        }

        if ( elasticsearchpluginPluginConfig.isEnabled()) {

        }
        else{
            return;
        }
        this.addApplicationTypeDetector(context);
        addElasticSearchPluginInterceptors();
    }
    /**
    * Blackcat profiler agent uses this detector to find out the service type of current application.
    */
    private void addApplicationTypeDetector(ProfilerPluginSetupContext context) {
        context.addApplicationTypeDetector(new ElasticSearchPluginProviderDetector(ElasticSearchPluginConstants.clazzInterceptors));
    }

    //  implementations
    private void addElasticSearchPluginInterceptors() {
        for (final ElasticSearchPluginInterceptorClassInfo interceptorClassInfo : ElasticSearchPluginConstants.clazzInterceptors) {
            transformTemplate.transform(interceptorClassInfo.getInterceptorClass(), new TransformCallback() {

                @Override
                public byte[] doInTransform(Instrumentor instrumentor, ClassLoader loader,
                                            String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                                            byte[] classfileBuffer) throws InstrumentException {

                    final InstrumentClass target = instrumentor.getInstrumentClass(loader, interceptorClassInfo.getInterceptorClass(), classfileBuffer);

                    final List<InstrumentMethod> methodsToTrace = target.getDeclaredMethods(interceptorClassInfo.getMethodFilter());
                    for (InstrumentMethod methodToTrace : methodsToTrace) {
                        String operationInterceptor = "com.navercorp.pinpoint.plugin.elasticsearchplugin.interceptor.ElasticSearchPluginOperationInterceptor";
                        methodToTrace.addScopedInterceptor(operationInterceptor, ElasticSearchPluginConstants.ElasticSearchPlugin_SCOPE, ExecutionPolicy.BOUNDARY);
                    }
                    return target.toBytecode();
                }
            });

        }
    }

    @Override
    public void setTransformTemplate(TransformTemplate transformTemplate) {
        this.transformTemplate = transformTemplate;
    }
}
