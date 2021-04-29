package com.nepxion.discovery.plugin.strategy.zuul.configuration;

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
import com.nepxion.discovery.plugin.strategy.zuul.adapter.ZuulStrategyDynamicRouteAdapter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.DefaultZuulStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.DefaultZuulStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.zuul.monitor.DefaultZuulStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.zuul.monitor.ZuulStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.zuul.wrapper.DefaultZuulStrategyCallableWrapper;
import com.nepxion.discovery.plugin.strategy.zuul.wrapper.ZuulStrategyCallableWrapper;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class ZuulStrategyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ZuulStrategyRouteFilter zuulStrategyRouteFilter() {
        return new DefaultZuulStrategyRouteFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    // 只用于调用链
    public ZuulStrategyClearFilter zuulStrategyClearFilter() {
        return new DefaultZuulStrategyClearFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public ZuulStrategyMonitor zuulStrategyMonitor() {
        return new DefaultZuulStrategyMonitor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
    public ZuulStrategyCallableWrapper zuulStrategyCallableWrapper() {
        return new DefaultZuulStrategyCallableWrapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicRouteAdapter zuulStrategyDynamicRouteAdapter(final ZuulProperties zuulProperties,
                                                               final ServerProperties serverProperties) {
        return new ZuulStrategyDynamicRouteAdapter(serverProperties.getServlet().getContextPath(), zuulProperties);
    }
}