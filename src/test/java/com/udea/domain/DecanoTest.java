package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DecanoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Decano.class);
        Decano decano1 = new Decano();
        decano1.setId(1L);
        Decano decano2 = new Decano();
        decano2.setId(decano1.getId());
        assertThat(decano1).isEqualTo(decano2);
        decano2.setId(2L);
        assertThat(decano1).isNotEqualTo(decano2);
        decano1.setId(null);
        assertThat(decano1).isNotEqualTo(decano2);
    }
}
