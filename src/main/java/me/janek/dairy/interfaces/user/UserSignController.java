package me.janek.dairy.interfaces.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.janek.dairy.common.exception.InvalidRequestException;
import me.janek.dairy.domain.user.UserCommand;
import me.janek.dairy.domain.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static me.janek.dairy.interfaces.user.UserDto.Info;
import static me.janek.dairy.interfaces.user.UserDto.Join;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserSignController {

    private final UserService userService;

    @GetMapping("/sign-in")
    public String signIn() {
        return "signIn";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Info> signUp(
        @Valid @RequestBody Join request,
        BindingResult bindingResult
    ) {
        if (!request.isPasswordEqual()) bindingResult.rejectValue("confirmedPassword", "400", "비밀번호가 일치하지 않습니다.");
        if (bindingResult.hasErrors()) throw new InvalidRequestException(bindingResult.getFieldErrors());

        var command = UserCommand.from(request);
        var signUpUser = new Info(userService.sighUpUser(command));

        return ResponseEntity.status(HttpStatus.CREATED).body(signUpUser);
    }

}
