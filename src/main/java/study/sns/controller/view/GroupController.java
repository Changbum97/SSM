package study.sns.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.sns.domain.dto.group.GroupAddRequest;
import study.sns.domain.entity.User;
import study.sns.service.GroupService;
import study.sns.service.UserService;

@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final UserService userService;
    private final GroupService groupService;

    @GetMapping("")
    public String groupMainPage(Authentication auth, Model model) {
        User user = userService.findByLoginId(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("groupList", groupService.getGroupList(user.getLoginId()));
        return "pages/groups/list";
    }

    @GetMapping("/add")
    public String groupNewPage(Authentication auth, Model model) {
        model.addAttribute("user", userService.findByLoginId(auth.getName()));
        model.addAttribute("groupAddRequest", new GroupAddRequest());
        return "pages/groups/add";
    }
}
