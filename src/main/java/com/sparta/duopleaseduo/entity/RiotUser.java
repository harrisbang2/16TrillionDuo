package com.sparta.duopleaseduo.entity;

import com.sparta.duopleaseduo.dto.response.RiotUserResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "riotusers")
public class RiotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String summonerName;
    private Long summonerLevel;
    private String tier;
    @Column(name = "ranks")
    private String rank;

    public RiotUser(RiotUserResponseDto responseDto) {
        this.summonerName = responseDto.getSummonerName();
        this.summonerLevel = responseDto.getSummonerLevel();
        this.tier = responseDto.getTier();
        this.rank = responseDto.getRank();
    }
}
