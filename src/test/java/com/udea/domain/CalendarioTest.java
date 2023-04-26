package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalendarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calendario.class);
        Calendario calendario1 = new Calendario();
        calendario1.setId(1L);
        Calendario calendario2 = new Calendario();
        calendario2.setId(calendario1.getId());
        assertThat(calendario1).isEqualTo(calendario2);
        calendario2.setId(2L);
        assertThat(calendario1).isNotEqualTo(calendario2);
        calendario1.setId(null);
        assertThat(calendario1).isNotEqualTo(calendario2);
    }
}
