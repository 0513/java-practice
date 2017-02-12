package net.tobebetter.proxy.sun;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by 0513 on 2017/1/8.
 */
public class BusinessProcessorHandlerTest {
    @Test
    public void shouldPrintExtraLog(){
        BusinessProcessorImpl bpimpl = new BusinessProcessorImpl();
        BusinessProcessorHandler handler = new BusinessProcessorHandler(bpimpl);
        BusinessProcessor bp = (BusinessProcessor) Proxy.newProxyInstance(bpimpl.getClass().getClassLoader(), bpimpl.getClass().getInterfaces(), handler);
        bp.processBusiness();
    }


}
