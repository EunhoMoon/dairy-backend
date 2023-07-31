package me.janek.dairy.interfaces.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.janek.dairy.common.exception.InvalidRequestException;
import me.janek.dairy.common.jwt.JwtFilter;
import me.janek.dairy.common.jwt.JwtTokenProvider;
import me.janek.dairy.domain.user.UserCommand;
import me.janek.dairy.domain.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.janek.dairy.interfaces.user.UserDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAuthController {

    private final UserService userService;

    private final JwtTokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> signIn(
        @Valid @RequestBody Login request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) throw new InvalidRequestException(bindingResult.getFieldErrors());

        var authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        var authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        var token = tokenProvider.createToken(authenticate.getName());

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(new JwtResponse(token));
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
