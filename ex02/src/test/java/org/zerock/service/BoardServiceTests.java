package org.zerock.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod_ = @Autowired)
	/*@Setter에서 사용된 onMethod 속성은 setService()에 @Autowired를 추가한다.
	 * @Autowired = 해당 인스턴스 변수가 스프링으로부터 자동으로 주입해 달라는 표시.*/
	private BoardService service;
//				ㄴ service객체가 주입되었나 확인.	
	
	//비즈니스 계층 구현 테스트
	@Test
	public void textExist() {
		
		log.info(service);
		assertNotNull(service);
		//service변수가 null이 아니어야만 테스트가 성공한다는 의미.
	}
	
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("새 글");
		board.setContent("새 내용");
		board.setWriter("newbie");
		
		service.register(board);
		//												BoardVO - private Long bno();
		log.info("생성된 게시물의 번호 : " + board.getBno());
		//												ㄴ BoardVO에서 게시글의 번호를 가져오는 메소드
	}
	
	@Test
	public void testGetList() {
		service.getList().forEach(board -> log.info(board)); //즉 전체글 리스트를 불러온다
						// 리스트 전체를 순회할 때 사용하는 메소드.
	}
	
	@Test
	public void testGet() {
		log.info(service.get(103L));
	}
	
	@Test
	public void testUpdate() {/*testUpdate()의 경우 특정한 게시물을 먼저 조회하고 title값을 수정한 후
	 								이를 업데이트 한다*/		
		BoardVO board = service.get(84L);		
		if(board == null) {
			return;
		}		
		board.setTitle("수정된 제목 입니다");
		log.info("MODIFY RESULT : " + service.modify(board));
	}
	
	@Test
	public void delete() {/*testDelete()의 경우 해당 게시글을 먼저 조회하고 
	 						true를 반환한다.*/
		log.info("REMOVE RESULT : " + service.remove(85L));
	}
}
