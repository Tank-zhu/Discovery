package com.nepxion.discovery.plugin.strategy.gateway.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @author Ning Zhang
 * @version 1.0
 */

import com.nepxion.discovery.plugin.framework.adapter.DynamicRouteAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.adapter.GatewayStrategyDynamicRouteAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.*;
import com.nepxion.discovery.plugin.strategy.gateway.monitor.DefaultGatewayStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.gateway.monitor.GatewayStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.gateway.wrapper.DefaultGatewayStrategyCallableWrapper;
import com.nepxion.discovery.plugin.strategy.gateway.wrapper.GatewayStrategyCallableWrapper;
import org.apache.skywalking.apm.agent.core.context.TracingContext;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class GatewayStrategyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new DefaultGatewayStrategyRouteFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public GatewayStrategyClearFilter gatewayStrategyClearFilter() {
        return new DefaultGatewayStrategyClearFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public GatewayStrategyMonitor gatewayStrategyMonitor() {
        return new DefaultGatewayStrategyMonitor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
    public GatewayStrategyCallableWrapper gatewayStrategyCallableWrapper() {
        return new DefaultGatewayStrategyCallableWrapper();
    }

    @ConditionalOnClass(TracingContext.class)
    protected static class SkywalkingStrategyConfiguration {
        @Bean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
        public SkyWalkingGatewayStrategyFilter skyWalkingGatewayStrategyFilter() {
            return new SkyWalkingGatewayStrategyFilter();
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicRouteAdapter gatewayStrategyDynamicRouteAdapter() {
        return new GatewayStrategyDynamicRouteAdapter();
    }
}