package com.shop.controller;

import com.shop.dto.ItemDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping(value = "/thymeleaf")
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String ex01(Model model) {
        model.addAttribute("data", "타임리프 예제 입니다.");
        return "thymeleaf/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String ex02(Model model) {
        ItemDto itemDto = ItemDto.builder()
                .itemNm("테스트 상품1")
                .itemDetail("상품 상세 설명")
                .price(10000)
                .regTime(LocalDateTime.now())
                .build();
        model.addAttribute("itemDto", itemDto);
        return "thymeleaf/thymeleafEx02";
    }

    @GetMapping(value = "/ex03")
    public String ex03(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = ItemDto.builder()
                    .itemNm("테스트 상품" + i)
                    .itemDetail("상품 상세 설명" + i)
                    .price(1000*i)
                    .regTime(LocalDateTime.now())
                    .build();
            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleaf/thymeleafEx03";
    }

    @GetMapping(value = "/ex04")
    public String ex04(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = ItemDto.builder()
                    .itemNm("테스트 상품" + i)
                    .itemDetail("상품 상세 설명" + i)
                    .price(1000*i)
                    .regTime(LocalDateTime.now())
                    .build();
            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleaf/thymeleafEx04";
    }

    @GetMapping(value = "/ex05")
    public String ex05(Model model) {
        return "thymeleaf/thymeleafEx05";
    }

    @GetMapping(value = "/ex06")
    public String ex06(String param1, String param2, Model model) {
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleaf/thymeleafEx06";
    }
}
