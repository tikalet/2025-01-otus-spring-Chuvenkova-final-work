package ru.otus.laboratory.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfiguration.class})
public class ControllerRestSecurityTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    @MockitoBean
    protected JwtDecoder jwtDecoder;

    @MockitoBean
    protected JwtEncoder jwtEncoder;

    protected void checkStatusAndRedirect(String user, List<GrantedAuthority> authorityList, int status,
                                          MockHttpServletRequestBuilder request) throws Exception {
        if (user != null) {
            request = request.with(user(user).authorities(authorityList));
        }

        mvc.perform(request).andExpect(status().is(status));
    }
}
