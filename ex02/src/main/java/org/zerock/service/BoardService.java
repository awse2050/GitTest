package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
//    void타입
public interface BoardService {

	public void register(BoardVO board);
	
	public BoardVO get(Long Bno); /*특정한 게시글을 가져오는 get()메소드의 경우
	 								처음부터 메소드의 리턴타입을 결정해 진행할 수 있다.*/
	
	public boolean modify(BoardVO board);
	
	public boolean remove(Long bno);
	
	public List<BoardVO> getList();
	/*게시글 전체목록을 가져오는 getList()의 경우 처음부터 메소드의 리턴타입을
	 * 결정해 진행할 수 있다.*/
}
