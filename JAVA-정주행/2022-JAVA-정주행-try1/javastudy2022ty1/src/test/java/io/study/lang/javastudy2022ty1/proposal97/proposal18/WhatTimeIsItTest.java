package io.study.lang.javastudy2022ty1.proposal97.proposal18;

import org.junit.jupiter.api.Test;

import java.time.*;

public class WhatTimeIsItTest {
    /**
     * 시간을 from -> to 로 변환한다고 할때,
     * withZoneSameZoneInstant 메서드는
     * 타임존을 바꾼 후의 ZonedDateTime 을 얻어낸다.
     * 이때 얻어낸 to의 시간은 from 에 대한 현지 시/분/초로 변환된다.
     *
     * ex)
     * 서올의 오늘날짜의 Asia/Seoul 타임기반의 6:23 -> 미국의 오늘날짜의 America/New_York 기반의 17:23:00
     */
    @Test
    public void withZoneSameInstant(){
        // 현재 시각을 얻어온다.
        LocalDateTime localDateTime1 = ZonedDateTime.now()
                .withZoneSameInstant(ZoneId.of("America/New_York"))
                .toLocalDateTime();

        System.out.println(localDateTime1);

        LocalDateTime localDateTime2 = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                .withZoneSameInstant(ZoneId.of("America/New_York"))
                .toLocalDateTime();

        System.out.println(localDateTime2);

        LocalDateTime korTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(6,23,0));
        ZonedDateTime zonedDateTime3 = ZonedDateTime.of(korTime, ZoneId.of("Asia/Seoul"))
                .withZoneSameInstant(ZoneId.of("America/New_York"));
        System.out.println(zonedDateTime3);
    }

    /**
     * 시간을 from -> to 로 변환한다고 할때,
     * withZoneTimeLocal 메서드는
     * 타임존을 바꾼 후의 ZonedDateTime 을 얻어낸다.
     * 단, from 의 시/분/초는 유지한 채로 타임존만 변경한다.
     * (가능한 한도 내에서 시/분/초를 유지한다고 공식문서에서 이야기하는 걸로 봐서는, 불가능한 경우 역시도 존재)
     *
     * ex)
     * 서울의 오늘날짜의 Asia/Seoul 타임기반의 6:05:00 -> 미국의 오늘날짜의 America/New_York 기반의 6:05:00
     */
    @Test
    public void withZoneSameLocal(){
        LocalDateTime korTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(6,5,0));

        ZonedDateTime zonedDateTime = ZonedDateTime.of(korTime, ZoneId.of("Asia/Seoul"))
                .withZoneSameLocal(ZoneId.of("America/New_York"));

        System.out.println(zonedDateTime);
    }
}
