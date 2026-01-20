package com.spring.security.pandeyar.config;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OneTimeTokenGenerationSuccessHandlerImpl implements OneTimeTokenGenerationSuccessHandler {


    @Value("${email.resend.api.key}")
    private String resendApiKey;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        var builder = UriComponentsBuilder.fromUriString(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .path("/login/ott")
                .queryParam("token", oneTimeToken.getTokenValue());
        String magicLink = builder.toUriString();
        System.out.println(magicLink);


        //Creating a link with token and sending it to email using resend api
        Resend resend = new Resend(resendApiKey);
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Arun's Spring One time Token App <onboarding@resend.dev>")
                .to("colloquy84@gmail.com")
                .subject("Magic link for you boot app")
                .html(magicLink)
                .build();
        try {
            CreateEmailResponse send = resend.emails().send(params);
            System.out.println(send);
        } catch (ResendException e) {
            e.printStackTrace();
        }

        System.out.println("Token Generated Successfully : " + oneTimeToken.getTokenValue());
//        response.getWriter().write("Token generated successfully. and sent to email id");
        response.sendRedirect("/success-token");
    }
}
