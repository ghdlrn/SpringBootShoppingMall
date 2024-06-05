package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@Transactional
@Log4j2
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

//    @PersistenceContext
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        Item item = queryFactory
                .select(qItem)
                .from(qItem)
                .where(qItem.itemNm.eq("한라산"))
                .fetchOne();
        log.info(item);
    }

    @Test
    @DisplayName("Querydsl 조회 테스트2")
    public void queryDslTest2() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        Item item = queryFactory
                .selectFrom(qItem)
                .where(qItem.itemNm.eq("라면").and(qItem.itemDetail.eq("상세1")))
                .fetchOne();
        log.info(item);
    }

    @Test
    @DisplayName("Querydsl 조회 테스트3")
    public void queryDslTest3() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        List<Item> item = queryFactory
                .selectFrom(qItem)
                .where(qItem.itemNm.eq("라면"))
                .orderBy(qItem.price.desc())
                .fetch();
        log.info(item);
    }

    @Test
    @DisplayName("Querydsl 조회 테스트4")
    public void queryDslTest4() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        List<Item> item = queryFactory
                .selectFrom(qItem)
                .where(qItem.price.goe(20000))
                .fetch();
        log.info(item);
    }

    @Test
    @DisplayName("Querydsl 조회 테스트5")
    public void queryDslTest5() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        createItemList2();
        BooleanBuilder builder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트";
        int price = 10003;
        String itemSellStat = "SELL";
        builder.and(item.itemDetail.like("%" + itemDetail + "%"));
        builder.and(item.price.eq(price));
        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }
        Pageable pageable = PageRequest.of(0,5);
        Page<Item> itemPagingResult = itemRepository.findAll(builder, pageable);
        log.info("----------------------------------------------------------------");
        log.info(itemPagingResult);
        log.info("----------------------------------------------------------------");
        log.info("total elements : " + itemPagingResult.getTotalElements());
        log.info("total page : " + itemPagingResult.getTotalPages());
        itemPagingResult.getContent().forEach(log::info);
    }

//    @Commit
    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = Item.builder()
                .itemNm("라면")
                .price(1500)
                .itemDetail("라면 상세 설명2")
                .itemSellStatus(ItemSellStatus.SELL)
                .stockNumber(100)
                .regTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        Item savedItem = itemRepository.save(item);
        log.info(savedItem.toString());
    }

    @Test
    @DisplayName("ID번 검색")
    public void findByIdTest() {
        Item item = itemRepository.findById(5L).get();
        log.info("Id : " + item);
    }

    @Test
    @DisplayName("라면 검색")
    public void findByNameTest() {
        Item item = itemRepository.findItemByItemNm("라면");
        log.info("라면 : " + item);
    }
/*
    @Test
    @DisplayName("라면 검색")
    public void findByItemDetailTest() {
        Item item = itemRepository.findByItemDetail("라면 상세 설명");
        log.info("라면 상세 설명 : " + item);
    }

    @Test
    @DisplayName("라면 & 라면 상세 검색")
    public void findByItemNmAndItemDetailTest() {
        Item item = itemRepository.findByItemNmAndItemDetail("라면","라면 상세 설명");
        log.info("라면 & 라면 상세 설명 : " + item);
    }
*/
    @Test
    @DisplayName("만원 미만 검색")
    public void findByPriceLessThanTest() {
        List<Item> list = itemRepository.findByPriceLessThan(10000);
        list.forEach(log::info);
    }

    @Test
    @DisplayName("만원 미만 검색 and 내림차순 정렬")
    public void findByPriceLessThanOrderByPriceDescTest() {
        List<Item> list = itemRepository.findByPriceLessThanOrderByPriceDesc(10000);
        list.forEach(log::info);
    }

    @Test
    @DisplayName("라면 상세 설명")
    public void findByItemDetail() {
        List<Item> item = itemRepository.findByItemDetail("상품");
        item.forEach(log::info);
    }

    @Test
    @DisplayName("라면 & 라면 상세 설명")
    public void findByItemNmAndItemDetail() {
        List<Item> item = itemRepository.findByItemNmAndItemDetail("라면", "라면 상세 설명");
        item.forEach(log::info);
    }

    @Test
    @DisplayName("findByItemDetailByNative")
    public void findByItemDetailByNative() {
        List<Item> item = itemRepository.findByItemDetailByNative("라면");
        item.forEach(log::info);
    }

    public void createItemList2(){
        for(int i = 1; i <= 5; i++){
            Item item = Item.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 + i)
                    .itemDetail("테스트 상품 상세 설명" + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .stockNumber(100)  // 오타 수정
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            Item savedItem = itemRepository.save(item);
        }
        for(int i = 6; i <= 10; i++){
            Item item = Item.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 + i)
                    .itemDetail("테스트 상품 상세 설명" + i)
                    .itemSellStatus(ItemSellStatus.SOLD_OUT)
                    .stockNumber(0)  // 오타 수정
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            Item savedItem = itemRepository.save(item);
        }
    }

}