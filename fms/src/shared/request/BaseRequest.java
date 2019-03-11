package shared.request;

import server.exceptions.InvalidParameterException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import static java.lang.String.format;

abstract class BaseRequest {

    protected static Logger log = Logger.getLogger("family-map-server");

    protected boolean verifyParams() throws InvalidParameterException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        boolean valid = false;

        Class klazz = this.getClass();
        BeanInfo concreteInfo = Introspector.getBeanInfo(klazz);
        for (PropertyDescriptor desc : concreteInfo.getPropertyDescriptors()) {
            String name = desc.getName();
            Object value = desc.getReadMethod().invoke(klazz);
            log.info(format("%s='%s'", name, value));
        }
        return valid;
    }
}
