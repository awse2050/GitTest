package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller //@Controller 어노테이션을 추가해 스프링의 빈으로 인식할 수 있게 한다.
@Log4j
@RequestMapping("/board/*") /*baord이하의 모든 url을 경로로 지정. 
 									/board로 시작하는 모든 처리를 BoardController가 하도록 지정.*/
@AllArgsConstructor //모든 파라미터를 이용하는 생성자.
public class BoardController {

	/*만약 @AllArgsConstructor를 이용해 생성자를 만들지 않았을경우
	 * @Setter(onMethod_ = @Autowired)를 이용해서 처리한다.*/
	private BoardService service;
	
	@GetMapping("/list")
	
	/*list()는 나중에 게시글 목록을 전달해야 하므로 Model을 매개변수로 지정하고
	 * 이를 통해 BoardServiceImpl객체의 getList()결과를 담아 전달한다.(allAttribute)*/
	public void list(Model model) {
		//				ㄴModel을 매개변수로 전달받고 있다.
		log.info("list");//		key     value
		model.addAttribute("list", service.getList());
	}/*매개변수로 전달받은 model.addAttribute(key, value); 메소드를 이용해
	 	View에 전달할 데이터를 key, value로 전달 가능.*/
	
	@PostMapping("/register")// 				일회성으로 데이터를 전달하는 용도.
	public String register(BoardVO board, RedirectAttributes rttr) {
//			ㄴString을 리턴타입으로 지정		파라미터로 지정. 이는 등록 작업이 끝난 후 다시 
/*												목록화면으로 이동하기 위함인데 추가적으로 새롭게 
 * 												등록된 게시물이 번호를 전달하기위해 사용.*/
		log.info("register : " + board);
		
		service.register(board);
		//							이름		값
		rttr.addFlashAttribute("result", board.getBno());
		/* addFlashAttribute(이름, 값) 메소드를 이용해서 화면에 한번만 사용하고 다음에는 사용하지
		 * 않는 데이터를 전달하기 위해서 사용*/
		
		return "redirect:/board/list";
// 리턴시에는 접두사로 redirect: 를 사용하는데 이를 이용하면 스프링mvc가 내부적으로 
//response.sendRedirect()를 처리해주기 때문에 편리하다.
	}
	
	@GetMapping("/get")//							화면쪽에서 해당번호의 게시물을 전달해야하므로
						//								Model을 매개변수로 전달받고 있다.
	public void get(@RequestParam("bno") Long bno, Model model) {
		//				bno의 값을 좀 더 명시적으로 처리하기 위해 @RequestParam을 썼다.
		
		log.info("/get");
		model.addAttribute("board", service.get(bno));
// 		매개변수로 전달받은 model.addAttribute(key, value); 메소드를 이용해 View에 전달할
//		데이터를 key, value로 전달 가능.
	}
	
	@PostMapping("/modify")//				일회성으로 데이터를 전달하는 용도,
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify " + board);
		
		if(service.modify(board)) {/*service.modify()는 수정여부를 boolean으로 처리하므로
		 								이를 이용해서 RedirectAttribute에 추가한다*/
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")//									일회성으로 데이터를 처리하는 용도
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
/*					remove()는 삭제후 페이지의 이동이 필요하므로 RedirectAttribute를 파라미터로 사용 */		
		log.info("remove........" + bno);
		if(service.remove(bno)) {
			rttr.addAttribute("result", "success");
		}
		return "redirect:/board/list";
		//		redirect를 이용해서 삭제처리 후에 다시 목록페이지로 이동한다.
	}

	// 글 등록 페이지 화면 구현.
	@GetMapping("/register")
	public void register() {
		
	}
}
