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

import com.navercorp.pinpoint.common.trace.AnnotationKey;
import com.navercorp.pinpoint.common.trace.AnnotationKeyMatchers;
import com.navercorp.pinpoint.common.trace.TraceMetadataProvider;
import com.navercorp.pinpoint.common.trace.TraceMetadataSetupContext;

import static com.navercorp.pinpoint.plugin.elasticsearchplugin.ElasticSearchPluginConstants.ElasticSearchPlugin;
import static com.navercorp.pinpoint.plugin.elasticsearchplugin.ElasticSearchPluginConstants.ElasticSearchPlugin_EVENT;

/**
 *
 * @author yinbp
 */
public class ElasticSearchPluginMetadataProvider implements TraceMetadataProvider {

    @Override
    public void setup(TraceMetadataSetupContext context) {
        //context.addServiceType(ElasticSearchPlugin, AnnotationKeyMatchers.ARGS_MATCHER);
        context.addServiceType(ElasticSearchPlugin);
                context.addServiceType(ElasticSearchPlugin_EVENT);
                //context.addServiceType(ElasticSearchPlugin, AnnotationKeyMatchers.exact(AnnotationKey.ARGS0));
        context.addAnnotationKey(ElasticSearchPluginConstants.ARGS_ANNOTATION_KEY);
    }

}
