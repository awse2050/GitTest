package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service/*가장 중요한 어노테이션.
계층 구조상 주로 비즈니스 영역을 담당하는 객체임을 표시하기 위해 사용.
작성된 어노테이션은 패키지를 읽어들이는동안 처리된다.*/

@AllArgsConstructor //모든 파라미터를 이용하는 생성자를 만든다.
public class BoardServiceImpl implements BoardService{
	
	//lombok을 이용하면 아래와 같이 처리할 수 있다.
	@Setter(onMethod_ = @Autowired) //Setter에서 사용된 onMethod_ 속성은
										// setMapper()에 @Autowired어노테이션을 추가한다.
	//스프링 4.3 이상에서 자동 처리
	private BoardMapper mapper; //BoardServiceImpl이 정상적으로 동작하기 위해선 
									//BoardMapper 객체가 필요하다

	@Override
	public void register(BoardVO board) {
		log.info("register,,,,,,,,,," + board);
		
		//	mapper의 insertSelectKey()를 이용해 나중에 생성된 게시물의 번호를 확인할 수 있게 작성.
		mapper.insertSelectKey(board);/*void타입으로 설계되어 있으므로 mapper.insertSelectKey()의
		 									반환값인 int를 사용하지 않고 있지만, 필요하다면 
		 									예외처리나 void대신에 int타입을 이용해서 사용가능.*/
	}

	@Override
	public BoardVO get(Long bno) {
	
		log.info("get....." + bno);
		
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		
		log.info("modify : " + board);
		
		//정상적으로 수정 처리가되면 1이라는 값을 반환되기 때문에 ==연산자를 이용해서
		//true false를 처리할 수 있다.
		return mapper.update(board) == 1; 
	}

	@Override
	public boolean remove(Long bno) {
		
		log.info("remove : " + bno);

		//정상적으로 삭제 처리가되면 1이라는 값을 반환되기 때문에 ==연산자를 이용해서
		//true false를 처리할 수 있다.
		return mapper.delete(bno) == 1;
	}

	@Override
	public List<BoardVO> getList() { //전체글 리스트를 구하는 getList();
		log.info("getList...............");
		return mapper.getList(); //mapper에서 getList()를 가져온다
	}
}
