package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ViceDecanoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViceDecano.class);
        ViceDecano viceDecano1 = new ViceDecano();
        viceDecano1.setId(1L);
        ViceDecano viceDecano2 = new ViceDecano();
        viceDecano2.setId(viceDecano1.getId());
        assertThat(viceDecano1).isEqualTo(viceDecano2);
        viceDecano2.setId(2L);
        assertThat(viceDecano1).isNotEqualTo(viceDecano2);
        viceDecano1.setId(null);
        assertThat(viceDecano1).isNotEqualTo(viceDecano2);
    }
}
