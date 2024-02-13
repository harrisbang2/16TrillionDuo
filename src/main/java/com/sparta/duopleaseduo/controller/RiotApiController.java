package com.sparta.duopleaseduo.controller;

import com.sparta.duopleaseduo.dto.response.RiotUserIdResponseDto;
import com.sparta.duopleaseduo.dto.response.RiotUserInfoResponseDto;
import com.sparta.duopleaseduo.dto.response.RiotUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/riot")
public class RiotApiController {

    @Value("${riot.api.key}")
    private String riotApiKey;

    @GetMapping("/{summonerName}")
    public RiotUserResponseDto findUserId(@PathVariable(name = "summonerName") String summonerName) {

        String serverUrl = "https://kr.api.riotgames.com";
        String summonerIdUrl = serverUrl + "/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" +riotApiKey;

        RestTemplate restTemplate = new RestTemplate();
        RiotUserIdResponseDto userIdResponseDto= restTemplate.getForObject(summonerIdUrl, RiotUserIdResponseDto.class);
        log.info("소환사 기본정보 조회 성공");

        RiotUserResponseDto userResponseDto = new RiotUserResponseDto(userIdResponseDto);

        String summonerInfoUrl = serverUrl + "/lol/league/v4/entries/by-summoner/" + userIdResponseDto.getId() + "?api_key=" + riotApiKey;
        RiotUserInfoResponseDto[] responseDtos = restTemplate.getForObject(summonerInfoUrl, RiotUserInfoResponseDto[].class);

        if(responseDtos.length == 0) {
            log.info("소환사 언랭");
            return userResponseDto;
        }
        userResponseDto.addRiotUserTier(responseDtos[0]);
        log.info("소환사 랭크정보 조회 성공");
        return userResponseDto;
    }
}
