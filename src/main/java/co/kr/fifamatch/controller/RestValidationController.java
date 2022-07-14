package co.kr.fifamatch.controller;

import co.kr.fifamatch.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/validation")
@RestController
@RequiredArgsConstructor
public class RestValidationController {

    private final MatchService matchService;

    @GetMapping("/user")
    @ResponseBody
    public ResponseEntity checkUser(String nickname1, String nickname2) {
        try {
            if (matchService.getUsers(nickname1) == null || matchService.getUsers(nickname2) == null)
                return ResponseEntity.ok().body(false);
        } catch (Exception e) {
            return ResponseEntity.ok().body(false);
        }

        return ResponseEntity.ok().body(true);
    }

}
