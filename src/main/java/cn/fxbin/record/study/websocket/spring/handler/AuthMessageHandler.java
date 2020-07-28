package cn.fxbin.record.study.websocket.spring.handler;

import cn.fxbin.record.study.websocket.message.AuthRequest;
import cn.fxbin.record.study.websocket.message.AuthResponse;
import cn.fxbin.record.study.websocket.message.UserJoinNoticeRequest;
import cn.fxbin.record.study.websocket.spring.util.WebSocketUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

/**
 * AuthMessageHandler
 *
 * @author fxbin
 * @version v1.0
 * @since 2020/7/28 16:00
 */
@Component
public class AuthMessageHandler implements MessageHandler<AuthRequest> {
    @Override
    public void  execute(WebSocketSession session, AuthRequest message) {
        // 如果未传递 accessToken
        if (StringUtils.isEmpty(message.getAccessToken())) {
            WebSocketUtils.send(session, AuthResponse.TYPE,
                    new AuthResponse().setCode(1).setMessage("认证 accessToken 未传入"));
            return;
        }

        // 添加到 WebSocketUtil 中
        WebSocketUtils.addSession(session, message.getAccessToken()); // 考虑到代码简化，我们先直接使用 accessToken 作为 User

        // 判断是否认证成功。这里，假装直接成功
        WebSocketUtils.send(session, AuthResponse.TYPE, new AuthResponse().setCode(0));

        // 通知所有人，某个人加入了。这个是可选逻辑，仅仅是为了演示,
        // 考虑到代码简化，我们先直接使用 accessToken 作为 User
        WebSocketUtils.broadcast(UserJoinNoticeRequest.TYPE,
                new UserJoinNoticeRequest().setNickname(message.getAccessToken()));


    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}
