package com.emmanuelapp.gestiondestock.controller;

import com.emmanuelapp.gestiondestock.dto.auth.AuthenticationRequest;
import com.emmanuelapp.gestiondestock.dto.auth.AuthenticationResponse;
import com.emmanuelapp.gestiondestock.services.auth.ApplicationUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.emmanuelapp.gestiondestock.utils.Constants.APP_ROOT;
import static com.emmanuelapp.gestiondestock.utils.Constants.AUTHENTICATION_ENDPOINT;

@Controller
@RequestMapping(AUTHENTICATION_ENDPOINT)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailService userDetailService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
      //  authenticationManager.authenticate(
        //        new UsernamePasswordAuthenticationToken(
          //              request.getLogin(),
            //            request.getPassword()
              //  )

        //);
       // final UserDetails userDetails = userDetailService.loadUserByUsername(request.getLogin());

        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken("dummy_access_token").build());
    }
}
