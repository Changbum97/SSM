package study.sns.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import study.sns.domain.Response;
import study.sns.domain.dto.user.*;
import study.sns.domain.entity.User;
import study.sns.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api(description = "회원가입, 로그인")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입")
    public Response<UserDto> join(UserJoinRequest req) {
        return Response.success(userService.saveUser(req));
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "아이디, 비밀번호 입력 시 accessToken, refreshToken return")
    public Response<UserLoginResponse> login(UserLoginRequest req) {
        return Response.success(userService.login(req));
    }

    @GetMapping("/logout")
    @ApiIgnore
    public Response<String> logout(Authentication auth) {
        userService.logout(auth.getName());
        return Response.success("SUCCESS");
    }

    @PostMapping("/nickname")
    @ApiIgnore
    public Response<String> setNickname(@ModelAttribute UserSetNicknameRequest req) {
        return Response.success(userService.setNickname(req.getNickname(), req.getAccessToken()));
    }

    @GetMapping("/check-loginId")
    @ApiOperation(value = "Login Id 중복 체크 통과", notes = "true: 중복 X, false: 중복 O")
    public Response<Boolean> checkLoginId(@RequestParam String loginId) {
        Boolean pass = userService.checkLoginId(loginId);
        return Response.success(pass);
    }

    @GetMapping("/access-token")
    @ApiOperation(value = "ACCESS-TOKEN 재발급", notes = "ACCESS-TOKEN이 만료된 경우 => REFRESH-TOKEN으로 재발급")
    public Response<String> getAccessTokenByRefreshToken(@ApiIgnore @CookieValue(name = "accessToken") String accessToken) {
        // Access Token이 만료되거나 없는데 Refresh Token이 유효하다면 JwtTokenFilter에서 Cookie에 Access Token을 넣어줌
        // 쿠키에서 Access Token을 추출해 String으로 return
        return Response.success(accessToken);
    }


    @GetMapping("/check-nickname")
    @ApiOperation(value = "Nickname 중복 체크", notes = "true: 중복 X, false: 중복 O")
    public Response<Boolean> checkNickname(@RequestParam String nickname) {
        Boolean pass = userService.checkNickname(nickname);
        return Response.success(pass);
    }

    @GetMapping("/check-email")
    @ApiOperation(value = "Email 중복 체크", notes = "true: 중복 X, false: 중복 O")
    public Response<Boolean> checkEmail(@RequestParam String email) {
        Boolean pass = userService.checkEmail(email);
        return Response.success(pass);
    }

    @GetMapping("/test")
    @ApiOperation(value = "로그인 테스트")
    public Response<UserDto> loginTest(@ApiIgnore Authentication auth) {
        User loginUser = userService.findByLoginId(auth.getName());
        return Response.success(UserDto.of(loginUser));
    }
}
