package kuke.board.articleread.cache;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OptimizedCacheTTLTest {
    @Test
    void ofTest() {
        //given
        long ttlSeconds = 10;

        //when
        OptimizedCacheTTL optimizedCacheTTL = OptimizedCacheTTL.of(ttlSeconds);

        //then
        assertThat(optimizedCacheTTL.getLogicalTTL()).isEqualTo(Duration.ofSeconds(ttlSeconds));
        assertThat(optimizedCacheTTL.getPhysicalTTL()).isEqualTo(
                Duration.ofSeconds(ttlSeconds).plusSeconds(OptimizedCacheTTL.PHYSICAL_TTL_DELAY_SECONDS)
        );
    }

}
