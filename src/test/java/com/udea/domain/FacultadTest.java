package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FacultadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Facultad.class);
        Facultad facultad1 = new Facultad();
        facultad1.setId(1L);
        Facultad facultad2 = new Facultad();
        facultad2.setId(facultad1.getId());
        assertThat(facultad1).isEqualTo(facultad2);
        facultad2.setId(2L);
        assertThat(facultad1).isNotEqualTo(facultad2);
        facultad1.setId(null);
        assertThat(facultad1).isNotEqualTo(facultad2);
    }
}
