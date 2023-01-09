package org.gab.ClouDuck.aws.auth;

import com.amazonaws.services.pi.model.InvalidArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import org.gab.ClouDuck.aws.utils.AWSAccountUtils;
import org.gab.ClouDuck.aws.utils.AWSBucketsUtils;
import org.gab.ClouDuck.aws.utils.AWSObjectsUtils;
import org.gab.ClouDuck.controllers.BucketOperationController;
import org.gab.ClouDuck.controllers.ObjectOperationsController;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthUserHandler implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AWSSetup.class) != null
                && (parameter.getParameterType().equals(AWSObjectsUtils.class) || parameter.getParameterType().equals(AWSBucketsUtils.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeRequest, WebDataBinderFactory binderFactory) {

        if(!this.supportsParameter(parameter))
            return WebArgumentResolver.UNRESOLVED;

        var request = nativeRequest.getNativeRequest(HttpServletRequest.class);
        var id = request.getHeader("id");

        if(id == null)
            throw new InvalidArgumentException("Parameter 'id' has been required");

        var cl = parameter.getMethod().getDeclaringClass();

        if(cl.equals(ObjectOperationsController.class))
            return AWSAccountUtils.objectLogin(id);
        else if (cl.equals(BucketOperationController.class))
            return AWSAccountUtils.bucketLogin(id);
        else
            throw new ClassCastException("This class can't will used with bean of aws-client class");
    }
}
