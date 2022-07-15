package co.kr.fifamatch.controller;

import co.kr.fifamatch.model.dto.MatchDto;
import co.kr.fifamatch.model.vo.MatchVo;
import co.kr.fifamatch.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {
    @Value("${server.servlet.context-path}")
    private String contextPath;
    private final MatchService matchService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("contextPath", contextPath);
        return "/home/index";
    }

    @GetMapping("/search")
    public String search(RedirectAttributes redirectAttributes, MatchDto matchDto) {
        String accessId1 = matchService.getUsers(matchDto.getNickname1());
        List<String> matchList = matchService.findMatches(matchDto, accessId1);
        MatchVo matchVo = matchService.calculateWDL(matchList, matchDto);
        
        redirectAttributes.addFlashAttribute("matchInfo", matchVo);

        return "redirect:/";
    }
}
