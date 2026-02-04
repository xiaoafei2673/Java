package com.example.firstproject.security;
import com.example.firstproject.mapper.AuthMapper;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.firstproject.common.ApiException;
import com.example.firstproject.entity.UserToken;
import com.example.firstproject.mapper.User_TokenMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor{
    private final User_TokenMapper userTokenMapper;
    private final AuthMapper authMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String path = request.getRequestURI();
        if (path.startsWith("/api/v1/users")) {
            return true;
        }
        String auth = request.getHeader("Authorization");
        if (auth == null || auth.isBlank()) {
            throw ApiException.unauthorized("缺少请求头");
        }
        String token = auth.startsWith("Bearer") ? auth.substring(7).trim() : auth.trim();
        if(token.isEmpty()){
            throw ApiException.unauthorized("token为空");
        }
        UserToken ut = userTokenMapper.selectOne(
                new QueryWrapper<UserToken>().eq("token",token)
        );
        if (ut == null){
            throw ApiException.unauthorized("token 无效");
        }
        Instant exp = Instant.parse(ut.getExpiredAt());
        if(exp.isBefore(Instant.now())){
            throw ApiException.unauthorized("token 已过期");
        }
        CurrentUser.setUserId(ut.getUserId());

        String required = resolveRequiredPerm(handler);
        if (required != null && !required.isBlank()) {
            Long uid = ut.getUserId();
            Integer sa = authMapper.isSuperAdmin(uid);
            if (sa != null && sa > 0) {
                return true; // superadmin 永远放行
            }
            Integer cnt = authMapper.hasPermission(uid, required);
            if (cnt == null || cnt <= 0) {
                throw ApiException.forbidden("权限不足: " + required);
            }
        }
        return true;

    }
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable Exception ex) {
        // 必须清理，防止线程复用导致串号
        CurrentUser.clear();
    }
    private String resolveRequiredPerm(Object handler) {
        if (!(handler instanceof org.springframework.web.method.HandlerMethod hm)) {
            return null;
        }

        // 方法注解优先
        var onMethod = hm.getMethodAnnotation(com.example.firstproject.security.RequirePerm.class);
        if (onMethod != null) return onMethod.value();

        // 类注解其次
        var onType = hm.getBeanType().getAnnotation(com.example.firstproject.security.RequirePerm.class);
        if (onType != null) return onType.value();

        return null;
    }

}
