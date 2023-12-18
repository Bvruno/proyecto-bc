package com.nttdata.bc.usuarios.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SMSService {

    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;
    @Value("${twilio.templates.validar_cuenta}")
    private String TEMPLATE_SID;

    public Message enviarSMS(String numeroDestino, String mensaje) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String numeroTwilio = "+17028309298";
        String mensajePersonalizado = String.format("{{1}}: %s", mensaje);

        return Message
                .creator(
                        new PhoneNumber(numeroDestino),
                        new PhoneNumber(numeroTwilio),
                        TEMPLATE_SID
                ).setBody(
                        mensajePersonalizado
                ).create();

    }
}
