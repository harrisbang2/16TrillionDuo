package com.sparta.duopleaseduo.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RiotUserResponseDto {

    private String summonerName;
    private Long summonerLevel;
    private String tier;
    private String rank;

    public RiotUserResponseDto(RiotUserIdResponseDto responseDto) {
        this.summonerName = responseDto.getName();
        this.summonerLevel = responseDto.getSummonerLevel();
    }

    public void addRiotUserTier(RiotUserInfoResponseDto responseDto) {
        this.tier = responseDto.getTier();
        this.rank = responseDto.getRank();
    }
}
