package study.sns.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import study.sns.domain.Response;
import study.sns.domain.dto.group.GroupRequest;
import study.sns.domain.dto.group.GroupDto;
import study.sns.repository.GroupRepository;
import study.sns.repository.UserRepository;
import study.sns.service.GroupService;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Api(description = "그룹 기능")
public class GroupApiController {

    private final GroupService groupService;

    @PostMapping("")
    @ApiOperation(value = "그룹 추가")
    public Response<GroupDto> addGroup(GroupRequest req, @ApiIgnore Authentication auth) {
        return Response.success(groupService.addGroup(req, auth.getName()));
    }

    @GetMapping("/join")
    @ApiOperation(value = "그룹 참여")
    public Response<GroupDto> joinGroup(GroupRequest req, @ApiIgnore Authentication auth) {
        return Response.success(groupService.joinGroup(req, auth.getName()));
    }


    @GetMapping("/check-name")
    @ApiOperation(value = "그룹명 중복 체크 통과", notes = "true: 중복 X, false: 중복 O")
    public Response<Boolean> checkName(@RequestParam String name) {
        Boolean pass = groupService.checkName(name);
        return Response.success(pass);
    }

}