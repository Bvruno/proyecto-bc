package com.nttdata.bc.usuarios.services.impl;

import com.nttdata.bc.usuarios.services.SMSService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class SMSServiceImpl implements SMSService {

    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;
    @Value("${twilio.templates.validar_cuenta}")
    private String TEMPLATE_SID;
    @Value("${twilio.phone_number}")
    private String PHONE_NUMBER;

    public Mono<Message> enviarSMS(String numeroDestino, String mensaje) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String numeroTwilio = "whatsapp:"+PHONE_NUMBER;
        return Mono.just(Message
                .creator(
                        new PhoneNumber("whatsapp:"+numeroDestino),
                        new PhoneNumber(numeroTwilio),
                        TEMPLATE_SID)
                .setBody("Su código de validación es: *" + mensaje + "*")
                .create());

    }
}
