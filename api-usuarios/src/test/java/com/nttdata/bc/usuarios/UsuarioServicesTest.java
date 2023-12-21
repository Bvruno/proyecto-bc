package com.nttdata.bc.usuarios;

import static org.junit.jupiter.api.Assertions.*;

import com.nttdata.bc.usuarios.services.UsuarioServices;
import org.junit.jupiter.api.Test;

class UsuarioServicesTest {

  @Test
  void testGenerarCodigo() {
    UsuarioServices usuarioServices = new UsuarioServices();
    int codigo = usuarioServices.generarCodigo();
    assertTrue(
      codigo >= 100000 && codigo <= 999999,
      "Generated code is not within the expected range"
    );
  }

}
