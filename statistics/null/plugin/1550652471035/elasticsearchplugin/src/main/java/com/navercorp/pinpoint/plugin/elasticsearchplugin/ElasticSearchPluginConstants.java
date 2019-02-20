/**
*  Copyright 2008 biaoping.yin
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package com.navercorp.pinpoint.plugin.elasticsearchplugin;

import com.navercorp.pinpoint.common.trace.*;

import static com.navercorp.pinpoint.common.trace.ServiceTypeProperty.RECORD_STATISTICS;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yinbp
 */
public class ElasticSearchPluginConstants {
    public static final AnnotationKey ARGS_ANNOTATION_KEY = AnnotationKeyFactory.of(912, "912.args", AnnotationKeyProperty.VIEW_IN_RECORD_SET);

    public static final ServiceType ElasticSearchPlugin = ServiceTypeFactory.of(1028, "ElasticSearchPlugin",RECORD_STATISTICS);
    public static final String ElasticSearchPlugin_SCOPE = "ElasticSearchPlugin_SCOPE";
        public static final ServiceType ElasticSearchPlugin_EVENT = ServiceTypeFactory.of(1029, "ElasticSearchPluginEvent");
        public static final List<ElasticSearchPluginInterceptorClassInfo> clazzInterceptors = new ArrayList<ElasticSearchPluginInterceptorClassInfo>();
    static{
        ElasticSearchPluginInterceptorClassInfo interceptorClassInfo = null;
        List<ElasticSearchPluginMethodInfo> methodInfos = null;
        ElasticSearchPluginMethodInfo methodInfo = null;
                    interceptorClassInfo = new ElasticSearchPluginInterceptorClassInfo();
            interceptorClassInfo.setInterceptorClass("org.frameworkset.elasticsearch.client.RestSeachExecutor");
                            methodInfo = new ElasticSearchPluginMethodInfo();
                methodInfo.setFilterType(1);
                methodInfo.setName("*");
                methodInfo.setPattern(true);
                interceptorClassInfo.setAllAccept(methodInfo);
                        
            methodInfos = null;
                            methodInfos = new ArrayList<ElasticSearchPluginMethodInfo>();
                                    methodInfo = new ElasticSearchPluginMethodInfo();
                    methodInfo.setFilterType(1);
                    methodInfo.setName("*");
                    methodInfo.setPattern(true);
                    methodInfos.add(methodInfo);
                                        interceptorClassInfo.setInterceptorMehtods(methodInfos);
            interceptorClassInfo.setMethodFilter(new ElasticSearchPluginCustomMethodFilter(null,interceptorClassInfo));
            clazzInterceptors.add(interceptorClassInfo);
                }
}
